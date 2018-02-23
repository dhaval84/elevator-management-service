
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.isobar.test.elevator.management.service.model.Button.FLOOR;

public class FloorCommand extends FloorBasedCommand {

    @JsonCreator
    public FloorCommand(@JsonProperty("floor") int floor) {
        super(FLOOR, floor);
    }
}
