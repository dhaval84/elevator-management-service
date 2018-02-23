
package com.isobar.test.elevator.management.service.model;

import static com.isobar.test.elevator.management.service.model.Button.UP;

public class UpCommand extends FloorBasedCommand {

    public UpCommand(int floor) {
        super(UP, floor);
    }
}
