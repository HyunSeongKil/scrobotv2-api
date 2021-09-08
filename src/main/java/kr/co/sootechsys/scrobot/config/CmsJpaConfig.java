package kr.co.sootechsys.scrobot.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;


/**
 * jpa용 연결 환경
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = {"kr.co.sootechsys.scrobot.persistence" })

    public class CmsJpaConfig {
    @Value("${app.datasource.encrypted}")
    private Boolean encrypted;

    @Value("${app.datasource.cms.url}")
    private String url;

    @Value("${app.datasource.cms.jdbc-url}")
    private String jdbcUrl;

    @Value("${app.datasource.cms.username}")
    private String username;

    @Value("${app.datasource.cms.password}")
    private String password;

    @Value("${app.datasource.cms.driver-class-name}")
    private String driverClassName;

    /**
     * jpa analsConfig 초기화
     */
    @PostConstruct
    private void init() {
        log.info("{}", this);
    }

    /**
     * 데이터 소스 생성
     * 
     * @return
     */
    @Primary
    @Bean(name = "dataSource")
    // @ConfigurationProperties(prefix = "app.datasource.cms")
    public DataSource dataSource() {
        String _url = jdbcUrl;
        String _username = username;
        String _password = password;

        // if(encrypted){
        //     _url = CmmnCrypto.decrypt(url);
        //     _username = CmmnCrypto.decrypt(username);
        //     _password = CmmnCrypto.decrypt(password);
        // }
        return DataSourceBuilder.create()
                                .type(HikariDataSource.class)
                                .url(_url)
                                .username(_username)
                                .password(_password)
                                .build();
    }

    /**
     * 엔티티매니저 펙토리 생성
     * 
     * @param builder
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("kr.co.sootechsys").persistenceUnit("foo").build();
    }

    /**
     * JAP 트랜잭션 객체 생성
     * 
     * @param entityManagerFactory
     * @return
     */
    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
