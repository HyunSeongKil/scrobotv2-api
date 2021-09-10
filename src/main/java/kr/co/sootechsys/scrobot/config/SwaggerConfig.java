package kr.co.sootechsys.scrobot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger 설정
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }


    @Bean
    public Docket api() {

        RequestParameterBuilder aParameterBuilder = new RequestParameterBuilder();
        List<RequestParameter> aParameters = new ArrayList<RequestParameter>();

        aParameters.clear();

//        aParameterBuilder.name("HTTP_AUTH_ACCESS_ID").
//                in(ParameterType.HEADER).
//                query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).
//                build();
//        aParameters.add(aParameterBuilder.build());

        aParameterBuilder.name("X-AUTH-TOKEN").
                in(ParameterType.HEADER).
                query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).
                build();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .globalRequestParameters(aParameters)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.co.sootechsys.scrobot.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "scrobotv2-server",
                "scrobotv2-server",
                "1.0.0",
                "#",
                new Contact("Contact Me", "#", "#"),
                "Licenses",
                "#",
                new ArrayList<>()
        );
    }

}
