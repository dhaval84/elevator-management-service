
package com.isobar.test.elevator.management.service.model;

public abstract class SelectedElevatorCommand extends Command {

    private final int elevatorId;

    public SelectedElevatorCommand(Button button, int elevatorId) {
        super(button);
        this.elevatorId = elevatorId;
    }

    public int getElevatorId() {
        return elevatorId;
    }
}
