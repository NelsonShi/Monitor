package com.fast.monitorserver.configuration.db;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by sjlor on 2018/8/8.
 * 构建连接池
 */
@ServletComponentScan
@Configuration
public class DruidDBConfig {
    private static Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
    @Autowired
    private DBConfig dbConfig;

    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "192.168.2.25,127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    public DataSource dataSource() {
        return getDruidDataSource(dbConfig.getUsernamePrimary(), dbConfig.getPasswordPrimary(), dbConfig.getUrlPrimary(),dbConfig.getDriverClassNamePrimary());
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @Primary
    public DataSource secondaryDataSource() {
        return getDruidDataSource(dbConfig.getUsernameSecondary(), dbConfig.getPasswordSecondary(), dbConfig.getUrlSecondary(),dbConfig.getDriverClassNameSecondary());
    }

    private DruidDataSource getDruidDataSource(String username, String password, String url,String driverClassName) {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(dbConfig.getInitialSize());
        datasource.setMinIdle(dbConfig.getMinIdle());
        datasource.setMaxActive(dbConfig.getMaxActive());
        datasource.setMaxWait(dbConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(dbConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(dbConfig.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(dbConfig.getValidationQuery());
        datasource.setTestWhileIdle(dbConfig.isTestWhileIdle());
        datasource.setTestOnBorrow(dbConfig.isTestOnBorrow());
        datasource.setTestOnReturn(dbConfig.isTestOnReturn());
        datasource.setPoolPreparedStatements(dbConfig.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dbConfig.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(dbConfig.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter : {0}", e);
        }
        datasource.setConnectionProperties(dbConfig.getConnectionProperties());
        datasource.setUseGlobalDataSourceStat(dbConfig.isUseGlobalDataSourceStat());
        return datasource;
    }

}
