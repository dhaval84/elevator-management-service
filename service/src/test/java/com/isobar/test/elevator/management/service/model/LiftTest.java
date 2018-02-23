
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static org.junit.Assert.*;

public class LiftTest {

    @Test
    public void shouldTestModelIsCanonical() {
        Lift lift = new Lift(1, UP);
        Lift duplicate = new Lift(1, DOWN);
        Lift other = new Lift(2, UP);

        assertCanonical(lift, duplicate, other);
    }
}