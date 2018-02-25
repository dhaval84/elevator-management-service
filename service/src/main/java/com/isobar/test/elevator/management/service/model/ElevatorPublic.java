
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ElevatorPublic {

    private int id;
    private Direction direction;
    private Integer floor;
    private ElevatorState state;
    @JsonProperty(required = false)
    private Integer destinationFloor;

    public static ElevatorPublic from (Elevator elevator) {
        ElevatorPublic pub = new ElevatorPublic();
        pub.id = elevator.getId();
        pub.direction = elevator.getDirection();
        pub.floor = elevator.getFloor();
        pub.state = elevator.getState();
        return pub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(Integer destinationFloor) {
        this.destinationFloor = destinationFloor;
    }
}
