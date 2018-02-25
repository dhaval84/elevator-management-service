
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.ElevatorService;
import com.isobar.test.elevator.management.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/commands")
@RestController
public class CommandsController {

    @Value("${service.min.floor}")
    private int minFloor = 0;
    @Value("${service.max.floor}")
    private int maxFloor = 5;

    private final ElevatorService service;
    private final Map<Integer, Elevator> elevatorMap;

    @Autowired
    public CommandsController(ElevatorService service, Map<Integer, Elevator> elevatorMap) {
        this.service = service;
        this.elevatorMap = elevatorMap;
    }

    @RequestMapping(method = POST)
    @ResponseStatus(ACCEPTED)
    public void commandReceived(@RequestBody Command command) {
        System.out.println("Command Received: " + command);
        validateCommand(command);

        service.processCommand(command);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public String handleValidationExceptions(IllegalArgumentException exception) {
        System.out.println(exception.getMessage());
        return exception.getMessage();
    }

    private void validateCommand(Command command) {
        switch (command.getButton()) {
            case UP_BUTTON:
                UpCommand upCommand = (UpCommand) command;
                validateFloor(upCommand.getFloor(), minFloor, maxFloor - 1);
                break;
            case DOWN_BUTTON:
                DownCommand downCommand = (DownCommand) command;
                validateFloor(downCommand.getFloor(), minFloor + 1, maxFloor);
                break;
            case FLOOR_BUTTON:
                FloorCommand floorCommand = (FloorCommand) command;
                validateFloor(floorCommand.getFloor(), minFloor, maxFloor);
                validateElevatorId(floorCommand.getElevatorId());
                break;
            case STOP_BUTTON:
                validateElevatorId(((StopCommand) command).getElevatorId());
                break;
        }
    }

    private void validateElevatorId(Integer elevatorId) {
        elevatorMap.values().stream()
                .filter(elevator -> elevator.getId() == elevatorId)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ElevatorId: " + elevatorId));
    }

    private void validateFloor(Integer floor, int minFloor, int maxFloor) {
        if (floor == null || floor < minFloor || floor > maxFloor) {
            throw new IllegalArgumentException("Invalid Floor for the command: " + floor);
        }
    }
}
