
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.Application;
import com.isobar.test.elevator.management.service.model.Elevator;
import com.isobar.test.elevator.management.service.model.ElevatorPublic;
import com.isobar.test.elevator.management.service.routing.RoutingStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static com.isobar.test.elevator.management.service.model.ElevatorState.MOVING;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SupportControllerTest {

    private SupportController controller;
    @Mock
    private RoutingStrategy strategy;
    private Map<Integer, Elevator> elevatorMap = new Application().elevatorMap();


    @Before
    public void setup() {
        controller = new SupportController(elevatorMap, strategy);
    }

    @Test
    public void shouldProvideCorrectlyTransformedView() {
        when(strategy.getNextStop(1)).thenReturn(of(4));
        when(strategy.getNextStop(2)).thenReturn(of(1));
        Elevator elevator1 = elevatorMap.get(1);
        Elevator elevator2 = elevatorMap.get(2);

        elevator1.setDirection(UP);
        elevator1.setState(MOVING);
        elevator1.setFloor(2);

        elevator2.setDirection(DOWN);
        elevator2.setState(MOVING);
        elevator2.setFloor(4);

        List<ElevatorPublic> elevatorPublicList = controller.getElevatorStatus();

        ElevatorPublic pub1 = elevatorPublicList.stream().filter(pub -> pub.getId() == 1).findAny().get();
        ElevatorPublic pub2 = elevatorPublicList.stream().filter(pub -> pub.getId() == 2).findAny().get();

        assertEquals(UP, pub1.getDirection());
        assertEquals(MOVING, pub1.getState());
        assertEquals(2, pub1.getFloor());
        assertEquals((Integer) 4, pub1.getDestinationFloor());

        assertEquals(DOWN, pub2.getDirection());
        assertEquals(MOVING, pub2.getState());
        assertEquals(4, pub2.getFloor());
        assertEquals((Integer) 1, pub2.getDestinationFloor());
    }
}
