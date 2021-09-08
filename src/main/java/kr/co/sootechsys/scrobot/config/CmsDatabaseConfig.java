package kr.co.sootechsys.scrobot.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;


/**
 * mybatis용 디비 연결 환경
 */
@Slf4j
@Configuration
@MapperScan(value = "kr.co.sootechsys.scrobot.persistence", annotationClass = CmsConnMapper.class, sqlSessionFactoryRef = "cmsSqlSessionFactory")
@EnableTransactionManagement
public class CmsDatabaseConfig {

    @PostConstruct
    private void init() {        
        log.info("<<");
    }

    /**
     * Spring의 모든 적용 가능한 리소스 팩토리
     * 
     * @return
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * sql 세션 초기화
     * 
     * @param dataSource
     * @param context
     * @return
     * @throws Exception
     */
    @Bean(name = "cmsSqlSessionFactory")
    @Primary
    public SqlSessionFactory cmsSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
            ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        //
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:mybatis/cms/**/*.xml"));

        //
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

        //
        // log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionFactory));
        return sqlSessionFactory;
    }

    /**
     * 세션 템플릿 메서드
     * 
     * @param cmsSqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "cmsSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate cmsSqlSessionTemplate(SqlSessionFactory cmsSqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(cmsSqlSessionFactory);

        //
        // log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionTemplate));
        return sqlSessionTemplate;
    }
}
