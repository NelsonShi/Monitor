package com.fast.monitorserver.configuration.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Nelson on 2018/8/9.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DBConfig {
    @Value("${spring.datasource.primary.url}")
    private String urlPrimary;
    @Value("${spring.datasource.primary.username}")
    private String usernamePrimary;
    @Value("${spring.datasource.primary.password}")
    private String passwordPrimary;
    @Value("${spring.datasource.primary.driver-class-name}")
    private String driverClassNamePrimary;
    @Value("${spring.datasource.secondary.url}")
    private String urlSecondary;
    @Value("${spring.datasource.secondary.username}")
    private String usernameSecondary;
    @Value("${spring.datasource.secondary.password}")
    private String passwordSecondary;
    @Value("${spring.datasource.secondary.driver-class-name}")
    private String driverClassNameSecondary;

    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean useGlobalDataSourceStat;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;

    public String getUrlPrimary() {
        return urlPrimary;
    }

    public void setUrlPrimary(String urlPrimary) {
        this.urlPrimary = urlPrimary;
    }

    public String getUsernamePrimary() {
        return usernamePrimary;
    }

    public void setUsernamePrimary(String usernamePrimary) {
        this.usernamePrimary = usernamePrimary;
    }

    public String getPasswordPrimary() {
        return passwordPrimary;
    }

    public void setPasswordPrimary(String passwordPrimary) {
        this.passwordPrimary = passwordPrimary;
    }

    public String getDriverClassNamePrimary() {
        return driverClassNamePrimary;
    }

    public void setDriverClassNamePrimary(String driverClassNamePrimary) {
        this.driverClassNamePrimary = driverClassNamePrimary;
    }

    public String getUrlSecondary() {
        return urlSecondary;
    }

    public void setUrlSecondary(String urlSecondary) {
        this.urlSecondary = urlSecondary;
    }

    public String getUsernameSecondary() {
        return usernameSecondary;
    }

    public void setUsernameSecondary(String usernameSecondary) {
        this.usernameSecondary = usernameSecondary;
    }

    public String getPasswordSecondary() {
        return passwordSecondary;
    }

    public void setPasswordSecondary(String passwordSecondary) {
        this.passwordSecondary = passwordSecondary;
    }

    public String getDriverClassNameSecondary() {
        return driverClassNameSecondary;
    }

    public void setDriverClassNameSecondary(String driverClassNameSecondary) {
        this.driverClassNameSecondary = driverClassNameSecondary;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public boolean isUseGlobalDataSourceStat() {
        return useGlobalDataSourceStat;
    }

    public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
        this.useGlobalDataSourceStat = useGlobalDataSourceStat;
    }
}
