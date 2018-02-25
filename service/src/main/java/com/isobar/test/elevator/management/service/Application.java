
package com.isobar.test.elevator.management.service;

import com.isobar.test.elevator.management.service.model.Elevator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static org.test.elevator.api.ElevatorFactory.getElevatorFacade;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
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
}
