package com.opipo.junit.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.opipo.junit"})
public class JunitPojoApplication {

    @Autowired
    private TypeResolver typeResolver;

    @Value("${app.basepackage:com.opipo}")
    private String basePackage;

    @Value("${app.version:}")
    private String appVersion;

    @Value("${app.longname:Application}")
    private String baseName;

    @Value("${app.description:REST API}")
    private String baseDescription;

    @Value("${app.termsOfServiceUrl:urn:tos}")
    private String termsOfServiceUrl;

    @Value("${app.contact.name:Alberto Cebrian Medialdea}")
    private String contactName;

    @Value("${app.contact.url:https://github.com/albertinizao/junit-pojo}")
    private String contactUrl;

    @Value("${app.contact.email:albertocebrianmedialdea@gmail.com}")
    private String contactEmail;

    @Value("${app.license.value:APACHE 2.0}")
    private String licenseValue;

    @Value("${app.license.url:http://www.apache.org/licenses/LICENSE-2.0}")
    private String licenseUrl;


    public static void main(String[] args) {
        SpringApplication.run(JunitPojoApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * Method that init SpringFox Swagger
     *
     * @return {@link Docket} object
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(basePackage))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
                .pathMapping("/").genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        Lists.newArrayList(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("500 message").responseModel(new ModelRef("Error")).build()))
                .enableUrlTemplating(false).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfo(baseName, baseDescription, appVersion, termsOfServiceUrl,
                new Contact(contactName, contactUrl, contactEmail), licenseValue, licenseUrl, new ArrayList<>());
    }
}
