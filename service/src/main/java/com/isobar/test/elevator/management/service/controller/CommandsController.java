
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.ElevatorService;
import com.isobar.test.elevator.management.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/commands")
@RestController
public class CommandsController {

    private static final int MIN_FLOOR = 0;
    private static final int MAX_FLOOR = 5;

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
                validateFloor(upCommand.getFloor(), MIN_FLOOR, MAX_FLOOR - 1);
                break;
            case DOWN_BUTTON:
                DownCommand downCommand = (DownCommand) command;
                validateFloor(downCommand.getFloor(), MIN_FLOOR + 1, MAX_FLOOR);
                break;
            case FLOOR_BUTTON:
                FloorCommand floorCommand = (FloorCommand) command;
                validateFloor(floorCommand.getFloor(), MIN_FLOOR, MAX_FLOOR);
                validateElevatorId(floorCommand.getElevatorId());
                break;
            case STOP_BUTTON:
                StopCommand stopCommand = (StopCommand) command;
                validateElevatorId(stopCommand.getElevatorId());
                break;
        }
    }

    private void validateElevatorId(int elevatorId) {
        elevatorMap.values().stream()
                .filter(elevator -> elevator.getId() == elevatorId)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ElevatorId: " + elevatorId));
    }

    private void validateFloor(int floor, int minFloor, int maxFloor) {
        if (floor < minFloor || floor > maxFloor) {
            throw new IllegalArgumentException("Invalid Floor for the command: " + floor);
        }
    }
}
