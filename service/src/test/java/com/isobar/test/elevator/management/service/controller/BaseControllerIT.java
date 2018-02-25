
package com.isobar.test.elevator.management.service.controller;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public abstract class BaseControllerIT {

    public static final String HOST_URL = "http://localhost:8080";

    @ClassRule
    public static final SpringClassRule CLASS_RULE = new SpringClassRule();
    @Rule
    public SpringMethodRule methodRule = new SpringMethodRule();

    @Configuration
    public static class ITConfig {

        @Bean
        public TestRestTemplate testRestTemplate(
                ObjectProvider<RestTemplateBuilder> builderProvider,
                Environment environment) {
            RestTemplateBuilder builder = builderProvider.getIfAvailable();
            TestRestTemplate template = builder == null ? new TestRestTemplate()
                    : new TestRestTemplate(builder.build());
            template.setUriTemplateHandler(new LocalHostUriTemplateHandler(environment));
            return template;
        }
    }
}
