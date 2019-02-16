package com.sl.web.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.annotation.MapperScan;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = {"com.sl.**.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
public class DbConfiguration {
	@Value("${db.type}")
	private String dbType;
	@Value("${db.url}")
	private String dbUrl;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	@Value("${db.initialSize}")
	private int initialSize;
	@Value("${db.minIdle}")
	private int minIdle;
	@Value("${db.maxActive}")
	private int maxActive;
	
	@Bean
	@Primary
	public DruidDataSource druidDataSource() {
		DruidDataSource source = new DruidDataSource();
		source.setDbType(this.dbType);
		source.setUrl(this.dbUrl);
		source.setUsername(this.username);
		source.setPassword(this.password);
		source.setInitialSize(this.initialSize);
		source.setMinIdle(this.minIdle);
		source.setMaxActive(this.maxActive);
		source.setMaxWait(60000);
		source.setRemoveAbandoned(true);
		source.setRemoveAbandonedTimeout(180);
		source.setLogAbandoned(true);
		source.setTimeBetweenEvictionRunsMillis(60000);
		source.setMinEvictableIdleTimeMillis(300000);
		source.setValidationQuery("SELECT 1 FROM DUAL");
		source.setTestWhileIdle(true);
		source.setTestOnBorrow(false);
		source.setTestOnReturn(false);
		source.setPoolPreparedStatements(true);
		source.setMaxPoolPreparedStatementPerConnectionSize(20);
		
		return source;
    }
	
	@Bean
    public SqlSessionFactory sqlSessionFactory(DruidDataSource source) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(source);
        
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapperHelper(new MapperHelper());
        
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource source){
		return new DataSourceTransactionManager(source);
	}
}
