
package com.isobar.test.elevator.management.service.model;

public abstract class SelectedElevatorCommand extends Command {

    private final Integer elevatorId;

    public SelectedElevatorCommand(Button button, Integer elevatorId) {
        super(button);
        this.elevatorId = elevatorId;
    }

    public Integer getElevatorId() {
        return elevatorId;
    }
}
