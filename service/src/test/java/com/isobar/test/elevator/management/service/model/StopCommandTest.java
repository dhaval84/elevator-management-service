
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Button.STOP_BUTTON;
import static org.junit.Assert.*;

public class StopCommandTest {

    @Test
    public void shouldTestModelIsCanonical() {
        StopCommand command = new StopCommand(1);
        StopCommand duplicate = new StopCommand(1);
        StopCommand other = new StopCommand(2);

        assertCanonical(command, duplicate, other);
    }

    @Test
    public void shouldTestButtonMember() {
        assertEquals(STOP_BUTTON, new StopCommand(2).getButton());
    }
}