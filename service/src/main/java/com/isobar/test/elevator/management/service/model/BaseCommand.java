
package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "button")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StopCommand.class, name = "STOP"),
        @JsonSubTypes.Type(value = UpCommand.class, name = "UP"),
        @JsonSubTypes.Type(value = DownCommand.class, name = "DOWN"),
        @JsonSubTypes.Type(value = FloorCommand.class, name = "FLOOR"),
})
public abstract class BaseCommand {

    private final Button button;

    @JsonCreator
    public BaseCommand(@JsonProperty("button") Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final BaseCommand other = (BaseCommand) obj;
        return Objects.equals(getButton(), other.getButton());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getButton());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("button", button).toString();
    }
}
