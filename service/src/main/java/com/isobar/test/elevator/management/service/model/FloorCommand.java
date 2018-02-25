
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static com.isobar.test.elevator.management.service.model.Button.FLOOR_BUTTON;

public class FloorCommand extends SelectedElevatorCommand {

    private final Integer floor;

    @JsonCreator
    public FloorCommand(@JsonProperty("elevatorId") Integer elevatorId, @JsonProperty("floor") Integer floor) {
        super(FLOOR_BUTTON, elevatorId);
        this.floor = floor;
    }

    public Integer getFloor() {
        return floor;
    }

    public boolean has(int inputFloor) {
        return floor == inputFloor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final FloorCommand other = (FloorCommand) obj;
        return Objects.equals(getButton(), other.getButton())
                && Objects.equals(getElevatorId(), other.getElevatorId())
                && Objects.equals(floor, other.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getButton(), getElevatorId(), floor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("button", getButton())
                .append("elevatorId", getElevatorId())
                .append("floor", floor).toString();
    }
}
