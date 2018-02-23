
package com.isobar.test.elevator.management.service.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BaseCommand {

    private final Button button;

    public BaseCommand(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("button", button).toString();
    }
}
