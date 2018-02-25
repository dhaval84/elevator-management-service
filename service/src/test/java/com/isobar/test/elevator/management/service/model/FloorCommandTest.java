
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Button.FLOOR_BUTTON;
import static org.junit.Assert.*;

public class FloorCommandTest {

    @Test
    public void shouldTestModelIsCanonical() {
        FloorCommand command = new FloorCommand(1, 2);
        FloorCommand duplicate = new FloorCommand(1, 2);
        FloorCommand other = new FloorCommand(2, 2);

        assertCanonical(command, duplicate, other);
    }

    @Test
    public void shouldTestButtonMember() {
        assertEquals(FLOOR_BUTTON, new FloorCommand(1, 2).getButton());
    }
}