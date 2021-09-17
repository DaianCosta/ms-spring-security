package com.rasmoo.client.financescontroll.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${host.full.dns.auth.link}")
    private String authLink;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.rasmoo.client.financescontroll.v1.controller"))
                .paths(PathSelectors.any()).build().apiInfo(this.apiInfo().build())
                .securitySchemes(Collections.singletonList(this.securitySchema())).securityContexts(Arrays.asList(this.securityContext()));
    }

    private ApiInfoBuilder apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("FinancesControll");
        apiInfoBuilder.description("API para controle de financas pessoais");

        return apiInfoBuilder;
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authScope = new ArrayList<>();
        authScope.add(new AuthorizationScope("cw_logado", "acesso area logada"));
        authScope.add(new AuthorizationScope("cw_naologado", "acesso area nao logada"));

        List<GrantType> grantTypes = new ArrayList<>();
        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(this.authLink));

        return new OAuth("auth2-Schema", authScope, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope[] authScopes = new AuthorizationScope[2];
        authScopes[0] = new AuthorizationScope("cw_logado", "acesso area logada");
        authScopes[1] = new AuthorizationScope("cw_naologado", "acesso area nao logada");
        return Collections.singletonList(new SecurityReference("auth2-Schema", authScopes));
    }

}