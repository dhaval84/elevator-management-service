
package com.isobar.test.elevator.management.service.model;

public enum Button {
    UP_BUTTON("UP"),
    DOWN_BUTTON("DOWN"),
    STOP_BUTTON("STOP"),
    FLOOR_BUTTON("FLOOR");

    private final String buttonName;

    Button(String buttonName) {
        this.buttonName = buttonName;
    }
}
