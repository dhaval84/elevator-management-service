
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;

public class ElevatorTest {

    @Test
    public void shouldTestModelIsCanonical() {
        Elevator elevator = new Elevator(1, 0, UP, null, null);
        Elevator duplicate = new Elevator(1, 2, DOWN, null, null);
        Elevator other = new Elevator(2, 0, UP, null, null);

        assertCanonical(elevator, duplicate, other);
    }
}