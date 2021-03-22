/**
 * 
 */
package com.klb.portal.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.klb.portal.interceptor.ListLimitInterceptor;

/**
 * @author Administrator
 *
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页组件
        PaginationInnerInterceptor  pageInterceptor=new PaginationInnerInterceptor(DbType.POSTGRE_SQL);
        pageInterceptor.setMaxLimit(500L);
        //防止全表更新与删除组件
        BlockAttackInnerInterceptor blockAttackInnerInterceptor=new BlockAttackInnerInterceptor();
        //列表查询数据量限制
        ListLimitInterceptor queryLimitInterceptor=new ListLimitInterceptor(DbType.POSTGRE_SQL);
        queryLimitInterceptor.setMaxLimit(1500);
        interceptor.addInnerInterceptor(pageInterceptor);
        interceptor.addInnerInterceptor(queryLimitInterceptor);
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }
    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.ypl.audit.*.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
     
        scannerConfigurer.setBasePackage("com.klb.portal.crud.*.mapper*");
        return scannerConfigurer;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

}
