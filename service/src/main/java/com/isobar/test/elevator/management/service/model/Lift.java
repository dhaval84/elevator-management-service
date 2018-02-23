
package com.isobar.test.elevator.management.service.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Lift {

    private final int number;
    private boolean active = true;
    private Direction direction;
    private int floor;

    public Lift(int number, Direction direction) {
        this.number = number;
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Lift other = (Lift) obj;
        return Objects.equals(number, other.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("number", number)
                .append("active", active)
                .append("direction", direction)
                .append("floor", floor).toString();
    }
}
