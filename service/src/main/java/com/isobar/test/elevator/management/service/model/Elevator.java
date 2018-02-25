
package com.isobar.test.elevator.management.service.model;

import com.isobar.test.elevator.management.service.ArrivalEventDistributor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.test.elevator.api.ElevatorFacade;

import java.util.*;

import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static com.isobar.test.elevator.management.service.model.ElevatorState.*;

public class Elevator {

    private final int id;
    private Direction direction;
    private int floor;
    private ElevatorState state = WAITING;
    private final ElevatorFacade facade;
    private final ArrivalEventDistributor distributor;
    private volatile Set<Integer> upwardStopsList;
    private volatile Set<Integer> downwardStopsList;

    public Elevator(int id, int floor, Direction direction, ElevatorFacade facade, ArrivalEventDistributor distributor) {
        this.id = id;
        this.floor = floor;
        this.direction = direction;
        this.facade = facade;
        this.distributor = distributor;
        upwardStopsList = Collections.synchronizedSet(new TreeSet<>());
        downwardStopsList = Collections.synchronizedSet(new TreeSet<>(Collections.reverseOrder()));
    }

    public int getId() {
        return id;
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

    public Set<Integer> getUpwardStopsList() {
        return upwardStopsList;
    }

    public Set<Integer> getDownwardStopsList() {
        return downwardStopsList;
    }

    public void moveUp() {
        state = MOVING;
        direction = UP;
        facade.moveUpOneFloor();
    }

    public void moveDown() {
        state = MOVING;
        direction = DOWN;
        facade.moveDownOneFloor();
    }

    public void lockBreaks() {
        state = STOPPED;
        facade.lockBreaks();
    }

    public void unlockBreaks() {
        state = WAITING;
        facade.unlockBreaks();
    }

    public ArrivalEventDistributor getDistributor() {
        return distributor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Elevator other = (Elevator) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("direction", direction)
                .append("floor", floor)
                .append("state", state).toString();
    }
}
