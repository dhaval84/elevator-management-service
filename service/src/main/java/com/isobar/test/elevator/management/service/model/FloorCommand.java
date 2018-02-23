
package com.isobar.test.elevator.management.service.model;

import static com.isobar.test.elevator.management.service.model.Button.FLOOR;

public class FloorCommand extends FloorBasedCommand {

    public FloorCommand(int floor) {
        super(FLOOR, floor);
    }
}
