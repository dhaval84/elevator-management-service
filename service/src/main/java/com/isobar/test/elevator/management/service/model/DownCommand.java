
package com.isobar.test.elevator.management.service.model;

import static com.isobar.test.elevator.management.service.model.Button.DOWN;

public class DownCommand extends FloorBasedCommand {

    public DownCommand(int floor) {
        super(DOWN, floor);
    }
}
