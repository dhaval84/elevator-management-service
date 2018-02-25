
package com.isobar.test.elevator.management.service;

import com.isobar.test.elevator.management.service.model.Elevator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static org.test.elevator.api.ElevatorFactory.getElevatorFacade;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class Application {

    @Value("${service.number.of.elevators}")
    private int numberOfElevators = 2;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Map<Integer, Elevator> elevatorMap() {
        Map<Integer, Elevator> elevatorMap = new HashMap<>(numberOfElevators);
        for (int i = 1; i <= numberOfElevators; i++) {
            ElevatorCallbackImpl listener = new ElevatorCallbackImpl(i);
            elevatorMap.put(i, new Elevator(i, 0, UP, getElevatorFacade(i, listener), listener));
        }
        return elevatorMap;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.isobar.test.elevator.management.service.controller"))
                .paths(regex("/.*"))
                .build();

    }
}
