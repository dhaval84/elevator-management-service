
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Button.UP_BUTTON;
import static org.junit.Assert.assertEquals;

public class UpCommandTest {

    @Test
    public void shouldTestModelIsCanonical() {
        UpCommand command = new UpCommand(1);
        UpCommand duplicate = new UpCommand(1);
        UpCommand other = new UpCommand(2);

        assertCanonical(command, duplicate, other);
    }

    @Test
    public void shouldTestButtonMember() {
        assertEquals(UP_BUTTON, new UpCommand(1).getButton());
    }
}