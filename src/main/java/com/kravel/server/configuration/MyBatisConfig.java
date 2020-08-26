package com.kravel.server.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan({
        "com.kravel.server.auth.mapper",
        "com.kravel.server.api.member.mapper",
        "com.kravel.server.api.article.mapper",
        "com.kravel.server.api.admin.mapper"
})
@EnableTransactionManagement
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception{

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/**/*.xml");
        sessionFactory.setMapperLocations(res);
        sessionFactory.setTypeAliasesPackage("com.kravel.server/**");

        SqlSessionFactory factory = sessionFactory.getObject();
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);

        return factory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
