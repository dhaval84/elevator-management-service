
package com.isobar.test.elevator.management.service;

import org.test.elevator.api.ElevatorCallback;

public class ElevatorCallbackImpl implements ElevatorCallback, ArrivalEventDistributor {

    private final int elevatorId;
    private ArrivalEventListener listener;

    public ElevatorCallbackImpl(int elevatorId) {
        this.elevatorId = elevatorId;
    }

    @Override
    public void elevatorArrived(int floor) {
        listener.elevatorArrived(elevatorId, floor);
    }

    @Override
    public void registerListener(ArrivalEventListener listener) {
        this.listener = listener;
    }
}
