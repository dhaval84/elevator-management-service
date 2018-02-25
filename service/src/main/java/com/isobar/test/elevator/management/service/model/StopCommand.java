
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.isobar.test.elevator.management.service.model.Button.STOP_BUTTON;

public class StopCommand extends SelectedElevatorCommand {

    @JsonCreator
    public StopCommand(@JsonProperty("elevatorId") int elevatorId) {
        super(STOP_BUTTON, elevatorId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final StopCommand other = (StopCommand) obj;
        return Objects.equals(getButton(), other.getButton())
                && Objects.equals(getElevatorId(), other.getElevatorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getButton(), getElevatorId());
    }
}
