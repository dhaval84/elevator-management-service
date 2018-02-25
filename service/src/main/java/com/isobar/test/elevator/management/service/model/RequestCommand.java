/*
 * Copyright (c) Message4U Pty Ltd 2014-2018
 *
 * Except as otherwise permitted by the Copyright Act 1967 (Cth) (as amended from time to time) and/or any other
 * applicable copyright legislation, the material may not be reproduced in any format and in any way whatsoever
 * without the prior written consent of the copyright owner.
 */

package com.isobar.test.elevator.management.service.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public abstract class RequestCommand extends Command {

    private final int floor;
    private final Direction direction;

    public RequestCommand(Button button, Direction direction, int floor) {
        super(button);
        this.direction = direction;
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public boolean has(Direction inputDirection, int inputFloor) {
        return inputDirection.equals(direction) && inputFloor == floor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final RequestCommand other = (RequestCommand) obj;
        return (Objects.equals(getButton(), other.getButton())
                || Objects.equals(direction, other.direction))
                && Objects.equals(floor, other.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getButton(), floor, direction);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("button", getButton())
                .append("floor", floor)
                .append("direction", direction).toString();
    }
}
