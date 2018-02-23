
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.isobar.test.elevator.management.service.model.Button.UP;

public class UpCommand extends FloorBasedCommand {

    @JsonCreator
    public UpCommand(@JsonProperty("floor") int floor) {
        super(UP, floor);
    }
}
