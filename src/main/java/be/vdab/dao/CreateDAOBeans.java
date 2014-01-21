package be.vdab.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("be.vdab.dao")
@PropertySource("classpath:/database.properties")
@EnableJpaRepositories(basePackageClasses={CreateDAOBeans.class})
public class CreateDAOBeans {

	@Autowired
	private Environment environment;

	@Bean(destroyMethod = "shutdown")
	HikariDataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(environment
				.getProperty("database.dataSourceClassName"));
		config.addDataSourceProperty("url",
				environment.getProperty("database.url"));
		config.addDataSourceProperty("user",
				environment.getProperty("database.user"));
		config.addDataSourceProperty("password",
				environment.getProperty("database.password"));
		return new HikariDataSource(config);
	}

	/*
	 * Je geeft de dataSource bean mee aan de JdbcTemplate constructor. De
	 * JdbcTemplate vraagt zijn databaseconnecties aan deze DataSource.
	 */
	/*
	 * @Bean JdbcTemplate jdbcTemplate() { return new
	 * JdbcTemplate(dataSource()); }
	 * 
	 * @Bean NamedParameterJdbcTemplate namedJdbcTemplate() { return new
	 * NamedParameterJdbcTemplate(dataSource()); }
	 */

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("be.vdab.entities",
				"be.vdab.valueobjects");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(environment.getProperty("database.showSql",
				Boolean.class));
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		return entityManagerFactoryBean;
	}

	@Bean
	JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}
}
