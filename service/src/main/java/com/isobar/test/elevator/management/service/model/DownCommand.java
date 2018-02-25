
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.isobar.test.elevator.management.service.model.Button.DOWN_BUTTON;
import static com.isobar.test.elevator.management.service.model.Direction.DOWN;

public class DownCommand extends RequestCommand {

    @JsonCreator
    public DownCommand(@JsonProperty("floor") int floor) {
        super(DOWN_BUTTON, DOWN, floor);
    }
}
