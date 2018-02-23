
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.model.Button.STOP;
import static org.junit.Assert.*;

public class StopCommandTest {

    @Test
    public void shouldTestButtonMember() {
        assertEquals(STOP, new StopCommand().getButton());
    }
}