
package com.isobar.test.elevator.management.service.model;

import org.junit.Test;

import static com.isobar.test.elevator.management.service.TestUtilities.assertCanonical;
import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;

public class DestinationRouteTest {

    @Test
    public void shouldTestModelIsCanonical() {
        DestinationRoute destinationRoute = new DestinationRoute(UP, 1);
        DestinationRoute duplicate = new DestinationRoute(UP, 1);
        DestinationRoute other = new DestinationRoute(DOWN, 1);

        assertCanonical(destinationRoute, duplicate, other);
    }
}