/*
 * Copyright (c) Message4U Pty Ltd 2014-2018
 *
 * Except as otherwise permitted by the Copyright Act 1967 (Cth) (as amended from time to time) and/or any other
 * applicable copyright legislation, the material may not be reproduced in any format and in any way whatsoever
 * without the prior written consent of the copyright owner.
 */

package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Button.DOWN;
import static org.junit.Assert.*;

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
        assertEquals(DOWN, new DownCommand(1).getButton());
    }
}