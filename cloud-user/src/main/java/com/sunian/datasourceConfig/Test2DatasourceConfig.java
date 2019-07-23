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
@MapperScan(basePackages = {"com.sunian.dao.mapper.test2"}, sqlSessionFactoryRef = "test2SessionFactory")
public class Test2DatasourceConfig {

    @Bean(name = "test2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2TransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("test2Datasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test2SessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("test2Datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sql/mapper/test2/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "test2SessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test2SessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
