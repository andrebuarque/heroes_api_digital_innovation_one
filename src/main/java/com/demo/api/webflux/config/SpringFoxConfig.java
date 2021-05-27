package com.demo.api.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    public static final String TITLE = "API de gerenciamento de super heróis";
    public static final String VERSION = "1.0";
    public static final String EMAIL = "andre.buarque90@gmail.com";
    public static final String URL_PAGE = "https://github.com/andrebuarque";
    public static final String NAME = "André Buarque";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .genericModelSubstitutes(Mono.class, Flux.class)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.demo.api.webflux.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(TITLE)
            .version(VERSION)
            .contact(new Contact(NAME, URL_PAGE, EMAIL))
            .build();
    }
}
