
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.isobar.test.elevator.management.service.model.Button.UP_BUTTON;
import static com.isobar.test.elevator.management.service.model.Direction.UP;

public class UpCommand extends RequestCommand {

    @JsonCreator
    public UpCommand(@JsonProperty("floor") Integer floor) {
        super(UP_BUTTON, UP, floor);
    }
}
