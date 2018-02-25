
package com.isobar.test.elevator.management.service.model;

import java.util.Objects;

public class DestinationRoute {

    private final Direction direction;
    private final int floor;

    public DestinationRoute(Direction direction, int floor) {
        this.direction = direction;
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final DestinationRoute other = (DestinationRoute) obj;
        return Objects.equals(direction, other.direction)
                && Objects.equals(floor, other.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, floor);
    }
}
