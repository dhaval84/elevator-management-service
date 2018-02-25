
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.model.Elevator;
import com.isobar.test.elevator.management.service.model.ElevatorPublic;
import com.isobar.test.elevator.management.service.routing.RoutingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.isobar.test.elevator.management.service.model.ElevatorState.MOVING;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/support")
@RestController
public class SupportController {

    private final Map<Integer, Elevator> elevatorMap;
    private final RoutingStrategy strategy;

    @Autowired
    public SupportController(Map<Integer, Elevator> elevatorMap, RoutingStrategy strategy) {
        this.elevatorMap = elevatorMap;
        this.strategy = strategy;
    }

    @RequestMapping(path = "/elevators", method = GET)
    @ResponseStatus(OK)
    public List<ElevatorPublic> getElevatorStatus() {
        List<ElevatorPublic> elevatorPublicList = elevatorMap.values().stream().map(ElevatorPublic::from).collect(Collectors.toList());
        addDestinationFloor(elevatorPublicList);
        return elevatorPublicList;
    }

    private void addDestinationFloor(List<ElevatorPublic> elevatorPublicList) {
        elevatorPublicList.forEach(elevatorPublic -> {
            if (elevatorPublic.getState().equals(MOVING)) {
                strategy.getNextStop(elevatorPublic.getId()).ifPresent(elevatorPublic::setDestinationFloor);
            }
        });
    }
}
