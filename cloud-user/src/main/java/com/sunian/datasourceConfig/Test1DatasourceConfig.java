package com.sunian.datasourceConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by liull on 2019/7/23.
 */
@Configuration
@MapperScan(basePackages = {"com.sunian.dao.mapper.test1"}, sqlSessionFactoryRef = "test1SessionFactory")
public class Test1DatasourceConfig {

    @Bean(name = "test1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test1TransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("test1Datasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test1SessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("test1Datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sql/mapper/test1/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "test1SessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test1SessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
