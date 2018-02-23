/*
 * Copyright (c) Message4U Pty Ltd 2014-2018
 *
 * Except as otherwise permitted by the Copyright Act 1967 (Cth) (as amended from time to time) and/or any other
 * applicable copyright legislation, the material may not be reproduced in any format and in any way whatsoever
 * without the prior written consent of the copyright owner.
 */

package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public abstract class FloorBasedCommand extends BaseCommand {

    private final int floor;

    public FloorBasedCommand(Button button, int floor) {
        super(button);
        this.floor = floor;
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

        final FloorBasedCommand other = (FloorBasedCommand) obj;
        return Objects.equals(getButton(), other.getButton()) &&
                Objects.equals(floor, other.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getButton(), floor);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("button", getButton())
                .append("floor", floor).toString();
    }
}
