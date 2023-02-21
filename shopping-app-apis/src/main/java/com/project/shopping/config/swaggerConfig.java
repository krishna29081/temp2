package com.project.shopping.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class swaggerConfig {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apikeys()
	{
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContext()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}
	
	private List<SecurityReference> sf()
	{
		AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
		return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[] {scope}));
	}

    @Bean
    Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContext())
                .securitySchemes(Arrays.asList(apikeys()))
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

	private ApiInfo getInfo() {
		
		return new ApiInfo("K&A : Clothing app","This project is developed by krishna " ,"1.0", "Terms of Service", null,
				"License Of APis", "API license Url", Collections.emptyList());
	}
}
