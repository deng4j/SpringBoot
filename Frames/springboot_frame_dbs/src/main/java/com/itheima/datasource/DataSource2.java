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

@Configuration
@MapperScan(basePackages = "com.itheima.bootdb2.mapper",sqlSessionFactoryRef = "bootdb2SqlSessionFactory")
public class DataSource2 {
    /**
     * 配置bootdb2数据库
     */
    @Bean(name = "bootdb2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.bootdb2")
    public DataSource testDatasource(){
        return DataSourceBuilder.create().build();
    }

    /**
     *创建SqlSessionFactory
     * 同一接口有相同多个实现时,Primary可以理解为默认优先选择
     */
    @Bean(name = "bootdb2SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("bootdb2Datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     *配置事务管理
     */
    @Bean(name = "bootdb2TransactionManager")
    public DataSourceTransactionManager testDataSourceTransactionManager(@Qualifier("bootdb2Datasource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "bootdb2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("bootdb2SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }










}
