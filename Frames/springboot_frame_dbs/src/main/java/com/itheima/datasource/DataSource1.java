package com.itheima.datasource;

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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Primary
@Configuration
@MapperScan(basePackages = "com.itheima.bootdb1.mapper",sqlSessionFactoryRef = "bootdb1SqlSessionFactory")
public class DataSource1 {
    /**
     * 配置bootdb1数据库
     */
    @Primary
    @Bean(name = "bootdb1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.bootdb1")
    public DataSource testDatasource(){
        return DataSourceBuilder.create().build();
    }

    /**
     *创建SqlSessionFactory
     * 同一接口有相同多个实现时,Primary可以理解为默认优先选择
     */
    @Bean(name = "bootdb1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("bootdb1Datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     *配置事务管理
     */
    @Primary
    @Bean(name = "bootdb1TransactionManager")
    public DataSourceTransactionManager testDataSourceTransactionManager(@Qualifier("bootdb1Datasource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "bootdb1SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("bootdb1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }










}
