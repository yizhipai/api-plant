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
 * 单次查询最大返回数据量限制
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
	 * 获取jsqlparser中count的SelectItem
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
    //最大查询条数
	protected Integer maxLimit;
	private DbType dbType;
	/**
	 * 方言实现类
	 * <p>
	 * 查看 {@link #findIDialect(Executor)} 逻辑
	 */
	private IDialect dialect;
    //当前SQL查询条数
	private Integer curCount;

	/**
	 * 这里进行count
	 */
	@Override
	public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
		if (page != null) {// 不处理分页
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

		if (this.getMaxLimit() == null || this.getCurCount() <= this.getMaxLimit()) {// 未设置最大查询数据量或当前查询小于最大限制，则直接返回
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
	 * 查询SQL拼接Order By
	 *
	 * @param originalSql 需要拼接的SQL
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
	 * 构建 mp 自用自动的 MappedStatement
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
	 * 获取自动优化的 countSql
	 *
	 * @param optimizeCountSql 是否进行优化
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
					// 包含groupBy 不去除orderBy
					canClean = false;
				}
				if (canClean) {
					for (OrderByElement order : orderBy) {
						// order by 里带参数,不去除order by
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
			// 包含 distinct、groupBy不优化
			if (distinct != null || null != groupBy) {
				return lowLevelCountSql(select.toString());
			}
			// 包含 join 连表,进行判断是否移除 join 连表
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
					/* 如果 join 里包含 ?(代表有入参) 或者 where 条件里包含使用 join 的表的字段作条件,就不移除 join */
					if (onExpressionS.contains(StringPool.QUESTION_MARK) || whereS.contains(str)) {
						canRemoveJoin = false;
						break;
					}
				}
				if (canRemoveJoin) {
					plainSelect.setJoins(null);
				}
			}
			// 优化 SQL
			plainSelect.setSelectItems(COUNT_SELECT_ITEM);
			return select.toString();
		} catch (Throwable e) {
			// 无法优化使用原 SQL
			logger.warn(
					"optimize this sql to a count sql has exception, sql:\"" + sql + "\", exception:\n" + e.getCause());
			return lowLevelCountSql(sql);
		}
	}

	/**
	 * 无法进行count优化时,降级使用此方法
	 *
	 * @param originalSql 原始sql
	 * @return countSql
	 */
	protected String lowLevelCountSql(String originalSql) {
		return SqlParserUtils.getOriginalCountSql(originalSql);
	}

	/**
	 * 获取分页方言类的逻辑
	 *
	 * @param executor Executor
	 * @return 分页方言类
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
