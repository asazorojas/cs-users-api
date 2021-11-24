package com.cornershopapp.usersapi.configuration.swagger;

import java.util.Set;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final ApplicationPropertyHolder applicationPropertyHolder;

    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .protocols(Set.of("http"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .paths(Predicate.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationPropertyHolder.getTitle())
                .description(applicationPropertyHolder.getDescription())
                .termsOfServiceUrl(applicationPropertyHolder.getTerms())
                .version(applicationPropertyHolder.getVersion())
                .build();
    }
}