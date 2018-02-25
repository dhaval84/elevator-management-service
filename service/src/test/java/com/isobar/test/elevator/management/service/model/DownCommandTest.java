
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Button.DOWN_BUTTON;
import static org.junit.Assert.assertEquals;

public class DownCommandTest {

    @Test
    public void shouldTestModelIsCanonical() {
        DownCommand command = new DownCommand(1);
        DownCommand duplicate = new DownCommand(1);
        DownCommand other = new DownCommand(2);

        assertCanonical(command, duplicate, other);
    }

    @Test
    public void shouldTestButtonMember() {
        assertEquals(DOWN_BUTTON, new DownCommand(1).getButton());
    }
}