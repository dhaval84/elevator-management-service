
package com.isobar.test.elevator.management.service;

import com.isobar.test.elevator.management.service.model.*;
import com.isobar.test.elevator.management.service.routing.RoutingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static com.isobar.test.elevator.management.service.model.ElevatorState.STOPPED;
import static com.isobar.test.elevator.management.service.model.ElevatorState.WAITING;

@Service
public class ElevatorService implements ArrivalEventListener {

    private final Map<Integer, Elevator> elevatorMap;
    private final RoutingStrategy strategy;

    @Autowired
    public ElevatorService(Map<Integer, Elevator> elevatorMap, RoutingStrategy strategy) {
        this.elevatorMap = elevatorMap;
        this.strategy = strategy;
        elevatorMap.values().forEach(elevator -> elevator.getDistributor().registerListener(this));
    }

    public void processCommand(Command command) {

        switch (command.getButton()) {
            case UP_BUTTON:
                strategy.addRouteToAnyElevator(new DestinationRoute(UP, ((RequestCommand) command).getFloor()));
                break;
            case DOWN_BUTTON:
                strategy.addRouteToAnyElevator(new DestinationRoute(DOWN, ((RequestCommand) command).getFloor()));
                break;
            case FLOOR_BUTTON:
                FloorCommand floorCommand = (FloorCommand) command;
                strategy.addStopToElevator(floorCommand.getElevatorId(), floorCommand.getFloor());
                break;
            case STOP_BUTTON:
                processStopCommand(((StopCommand) command).getElevatorId());
                break;
        }
        wakeUpElevatorsIfNecessary();
    }

    private void wakeUpElevatorsIfNecessary() {
        elevatorMap.values().forEach(elevator -> {
            if (elevator.getState().equals(WAITING)) {
                getAndProceedToNextStop(elevator);
            }
        });
    }

    private void processStopCommand(int elevatorId) {
        Elevator elevator = elevatorMap.get(elevatorId);
        if (elevator.getState().equals(STOPPED)) {
            elevator.unlockBreaks();
        } else {
            elevator.lockBreaks();
        }
    }

    @Override
    public void elevatorArrived(int elevatorId, int floor) {
        System.out.println("Elevator " + elevatorId + " arrived at Floor: " + floor + "\n");

        Elevator elevator = elevatorMap.get(elevatorId);
        elevator.setFloor(floor);

        checkIfToStopAndThenProceed(elevator);
    }

    private void checkIfToStopAndThenProceed(Elevator elevator) {
        if (strategy.shouldStopAndRemove(elevator.getId())) {
            stopAtThisFloor(elevator);
        }
        getAndProceedToNextStop(elevator);
    }

    private void getAndProceedToNextStop(Elevator elevator) {
        Optional<Integer> nextStop = strategy.getNextStop(elevator.getId());
        if (nextStop.isPresent()) {
            if (nextStop.get() != elevator.getFloor()) {
                move(elevator, nextStop.get());
            } else {
                System.out.println("Already at floor: " + nextStop.get());
                checkIfToStopAndThenProceed(elevator);
            }
        } else {
            System.out.println("Elevator" + elevator.getId() + ": Nothing to do, so Waiting...\n");
            elevator.setState(WAITING);
        }
    }

    private void stopAtThisFloor(Elevator elevator) {
        elevator.lockBreaks();
        // ----------- Door Opens -----------


        elevator.unlockBreaks();
        // ----------- Door Closes -----------
    }

    private void move(Elevator elevator, int floor) {
        // Same floor command already taken care of in proceedElevator
        if (floor > elevator.getFloor()) {
            elevator.moveUp();
        } else {
            // Change direction
            elevator.moveDown();
        }
    }
}
