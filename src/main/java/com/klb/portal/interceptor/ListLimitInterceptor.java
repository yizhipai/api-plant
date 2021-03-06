/**
 * 
 */
package com.klb.portal.interceptor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * @author hcs
 * ???????????????????????????????????????
 *
 */
@Data
@NoArgsConstructor
@SuppressWarnings({ "rawtypes" })
public class ListLimitInterceptor implements InnerInterceptor {
	protected static final Map<String, MappedStatement> countMsCache = new ConcurrentHashMap<>();
	protected static final List<SelectItem> COUNT_SELECT_ITEM = Collections.singletonList(defaultCountSelectItem());
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * ??????jsqlparser???count???SelectItem
	 */
	private static SelectItem defaultCountSelectItem() {
		Function function = new Function();
		ExpressionList expressionList = new ExpressionList(Collections.singletonList(new LongValue(1)));
		function.setName("COUNT");
		function.setParameters(expressionList);
		return new SelectExpressionItem(function);
	}

	public ListLimitInterceptor(DbType dbType) {
		this.dbType = dbType;
	}
    //??????????????????
	protected Integer maxLimit;
	private DbType dbType;
	/**
	 * ???????????????
	 * <p>
	 * ?????? {@link #findIDialect(Executor)} ??????
	 */
	private IDialect dialect;
    //??????SQL????????????
	private Integer curCount;

	/**
	 * ????????????count
	 */
	@Override
	public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
		if (page != null) {// ???????????????
			return true;
		}

		MappedStatement countMs = buildAutoCountMappedStatement(ms);
		String countSqlStr = autoCountSql(false, boundSql.getSql());
		PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
		BoundSql countSql = new BoundSql(countMs.getConfiguration(), countSqlStr, mpBoundSql.parameterMappings(),
				parameter);
		PluginUtils.setAdditionalParameter(countSql, mpBoundSql.additionalParameters());

		CacheKey cacheKey = executor.createCacheKey(countMs, parameter, rowBounds, countSql);
		Object result = executor.query(countMs, parameter, rowBounds, resultHandler, cacheKey, countSql).get(0);

		Integer count = Integer.parseInt(result.toString());
		this.setCurCount(count);

		return true;
	}

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
		if (null != page) {
			return;
		}

		if (this.getMaxLimit() == null || this.getCurCount() <= this.getMaxLimit()) {// ?????????????????????????????????????????????????????????????????????????????????
			return;
		}

	
		String buildSql = boundSql.getSql();

		IDialect dialect = findIDialect(executor);

		final Configuration configuration = ms.getConfiguration();
		DialectModel model = dialect.buildPaginationSql(buildSql, 0, this.getMaxLimit());
		PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);

		List<ParameterMapping> mappings = mpBoundSql.parameterMappings();
		Map<String, Object> additionalParameter = mpBoundSql.additionalParameters();
		model.consumers(mappings, configuration, additionalParameter);
		mpBoundSql.sql(model.getDialectSql());
		mpBoundSql.parameterMappings(mappings);
	}

	/**
	 * ??????SQL??????Order By
	 *
	 * @param originalSql ???????????????SQL
	 * @return ignore
	 */
	protected String concatOrderBy(String originalSql, List<OrderItem> orderList) {
		try {
			Select select = (Select) CCJSqlParserUtil.parse(originalSql);
			SelectBody selectBody = select.getSelectBody();
			if (selectBody instanceof PlainSelect) {
				PlainSelect plainSelect = (PlainSelect) selectBody;
				List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
				List<OrderByElement> orderByElementsReturn = addOrderByElements(orderList, orderByElements);
				plainSelect.setOrderByElements(orderByElementsReturn);
				return select.toString();
			} else if (selectBody instanceof SetOperationList) {
				SetOperationList setOperationList = (SetOperationList) selectBody;
				List<OrderByElement> orderByElements = setOperationList.getOrderByElements();
				List<OrderByElement> orderByElementsReturn = addOrderByElements(orderList, orderByElements);
				setOperationList.setOrderByElements(orderByElementsReturn);
				return select.toString();
			} else if (selectBody instanceof WithItem) {
				// todo: don't known how to resole
				return originalSql;
			} else {
				return originalSql;
			}
		} catch (JSQLParserException e) {
			logger.warn("failed to concat orderBy from IPage, exception:\n" + e.getCause());
			return originalSql;
		}
	}

	protected List<OrderByElement> addOrderByElements(List<OrderItem> orderList, List<OrderByElement> orderByElements) {
		orderByElements = CollectionUtils.isEmpty(orderByElements) ? new ArrayList<>(orderList.size())
				: orderByElements;
		List<OrderByElement> orderByElementList = orderList.stream()
				.filter(item -> StringUtils.isNotBlank(item.getColumn())).map(item -> {
					OrderByElement element = new OrderByElement();
					element.setExpression(new Column(item.getColumn()));
					element.setAsc(item.isAsc());
					element.setAscDescPresent(true);
					return element;
				}).collect(Collectors.toList());
		orderByElements.addAll(orderByElementList);
		return orderByElements;
	}

	/**
	 * ?????? mp ??????????????? MappedStatement
	 *
	 * @param ms MappedStatement
	 * @return MappedStatement
	 */
	protected MappedStatement buildAutoCountMappedStatement(MappedStatement ms) {
		final String countId = ms.getId() + "_mpCount";
		final Configuration configuration = ms.getConfiguration();
		return CollectionUtils.computeIfAbsent(countMsCache, countId, key -> {
			MappedStatement.Builder builder = new MappedStatement.Builder(configuration, key, ms.getSqlSource(),
					ms.getSqlCommandType());
			builder.resource(ms.getResource());
			builder.fetchSize(ms.getFetchSize());
			builder.statementType(ms.getStatementType());
			builder.timeout(ms.getTimeout());
			builder.parameterMap(ms.getParameterMap());
			builder.resultMaps(Collections.singletonList(
					new ResultMap.Builder(configuration, Constants.MYBATIS_PLUS, Long.class, Collections.emptyList())
							.build()));
			builder.resultSetType(ms.getResultSetType());
			builder.cache(ms.getCache());
			builder.flushCacheRequired(ms.isFlushCacheRequired());
			builder.useCache(ms.isUseCache());
			return builder.build();
		});
	}

	/**
	 * ????????????????????? countSql
	 *
	 * @param optimizeCountSql ??????????????????
	 * @param sql              sql
	 * @return countSql
	 */
	protected String autoCountSql(boolean optimizeCountSql, String sql) {
		if (!optimizeCountSql) {
			return lowLevelCountSql(sql);
		}
		try {
			Select select = (Select) CCJSqlParserUtil.parse(sql);
			PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
			Distinct distinct = plainSelect.getDistinct();
			GroupByElement groupBy = plainSelect.getGroupBy();
			List<OrderByElement> orderBy = plainSelect.getOrderByElements();

			if (CollectionUtils.isNotEmpty(orderBy)) {
				boolean canClean = true;
				if (groupBy != null) {
					// ??????groupBy ?????????orderBy
					canClean = false;
				}
				if (canClean) {
					for (OrderByElement order : orderBy) {
						// order by ????????????,?????????order by
						Expression expression = order.getExpression();
						if (!(expression instanceof Column)
								&& expression.toString().contains(StringPool.QUESTION_MARK)) {
							canClean = false;
							break;
						}
					}
				}
				if (canClean) {
					plainSelect.setOrderByElements(null);
				}
			}
			// #95 Github, selectItems contains #{} ${}, which will be translated to ?, and
			// it may be in a function: power(#{myInt},2)
			for (SelectItem item : plainSelect.getSelectItems()) {
				if (item.toString().contains(StringPool.QUESTION_MARK)) {
					return lowLevelCountSql(select.toString());
				}
			}
			// ?????? distinct???groupBy?????????
			if (distinct != null || null != groupBy) {
				return lowLevelCountSql(select.toString());
			}
			// ?????? join ??????,???????????????????????? join ??????
			List<Join> joins = plainSelect.getJoins();
			if (CollectionUtils.isNotEmpty(joins)) {
				boolean canRemoveJoin = true;
				String whereS = Optional.ofNullable(plainSelect.getWhere()).map(Expression::toString)
						.orElse(StringPool.EMPTY);
				for (Join join : joins) {
					if (!join.isLeft()) {
						canRemoveJoin = false;
						break;
					}
					Table table = (Table) join.getRightItem();
					String str = Optional.ofNullable(table.getAlias()).map(Alias::getName).orElse(table.getName())
							+ StringPool.DOT;
					String onExpressionS = join.getOnExpression().toString();
					/* ?????? join ????????? ?(???????????????) ?????? where ????????????????????? join ????????????????????????,???????????? join */
					if (onExpressionS.contains(StringPool.QUESTION_MARK) || whereS.contains(str)) {
						canRemoveJoin = false;
						break;
					}
				}
				if (canRemoveJoin) {
					plainSelect.setJoins(null);
				}
			}
			// ?????? SQL
			plainSelect.setSelectItems(COUNT_SELECT_ITEM);
			return select.toString();
		} catch (Throwable e) {
			// ????????????????????? SQL
			logger.warn(
					"optimize this sql to a count sql has exception, sql:\"" + sql + "\", exception:\n" + e.getCause());
			return lowLevelCountSql(sql);
		}
	}

	/**
	 * ????????????count?????????,?????????????????????
	 *
	 * @param originalSql ??????sql
	 * @return countSql
	 */
	protected String lowLevelCountSql(String originalSql) {
		return SqlParserUtils.getOriginalCountSql(originalSql);
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param executor Executor
	 * @return ???????????????
	 */
	protected IDialect findIDialect(Executor executor) {
		if (dialect != null) {
			return dialect;
		}
		if (dbType != null) {
			dialect = DialectFactory.getDialect(dbType);
			return dialect;
		}
		return DialectFactory.getDialect(JdbcUtils.getDbType(executor));
	}
}
