
package com.isobar.test.elevator.management.service.model;

public enum Direction {

    UP(1), DOWN(-1);

    private int multiplier;

    Direction(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
