
package com.isobar.test.elevator.management.service.model;

import static com.isobar.test.elevator.management.service.model.Button.STOP;

public class StopCommand extends BaseCommand {

    public StopCommand() {
        super(STOP);
    }
}
