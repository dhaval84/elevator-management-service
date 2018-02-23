
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.isobar.test.elevator.management.service.model.Button.DOWN;

public class DownCommand extends FloorBasedCommand {

    @JsonCreator
    public DownCommand(@JsonProperty("floor") int floor) {
        super(DOWN, floor);
    }
}
