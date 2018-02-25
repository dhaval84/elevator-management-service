
package com.isobar.test.elevator.management.service;

import com.isobar.test.elevator.management.service.model.*;
import com.isobar.test.elevator.management.service.routing.RoutingStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static com.isobar.test.elevator.management.service.model.ElevatorState.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ElevatorServiceTest {

    private ElevatorService service;
    private Map<Integer, Elevator> elevatorMap;
    private static Elevator elevator1;
    @Mock
    private RoutingStrategy strategy;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        initMocks(this);
        System.setOut(new PrintStream(outContent));
        elevatorMap = new Application().elevatorMap();
        elevator1 = elevatorMap.values().stream().filter(elevator -> elevator.getId() == 1).findAny().get();
        elevator1.setFloor(3);
        service = new ElevatorService(elevatorMap, strategy);
    }

    @Test
    public void shouldCheckCorrectFunctionIsCalledOnRoutingStrategy() {
        elevatorMap.values().forEach(elevator -> elevator.setState(MOVING));

        UpCommand upCommand = new UpCommand(3);
        service.processCommand(upCommand);
        verify(strategy).addRouteToAnyElevator(new DestinationRoute(UP, 3));

        DownCommand downCommand = new DownCommand(3);
        service.processCommand(downCommand);
        verify(strategy).addRouteToAnyElevator(new DestinationRoute(DOWN, 3));

        FloorCommand floorCommand = new FloorCommand(1, 3);
        service.processCommand(floorCommand);
        verify(strategy).addStopToElevator(1, 3);
    }

    @Test
    public void shouldCheckStopCommandWorkingProperly() {
        StopCommand stopCommand = new StopCommand(elevator1.getId());

        service.processCommand(stopCommand);
        assertEquals(STOPPED, elevator1.getState());

        service.processCommand(stopCommand);
        assertEquals(WAITING, elevator1.getState());
    }

    @Test
    public void shouldStopOnFloorIfRequestedOnCallback() {
        when(strategy.shouldStopAndRemove(elevator1.getId())).thenReturn(true);

        service.elevatorArrived(elevator1.getId(), 4);

        assertEquals(4, elevator1.getFloor());
        assertTrue(outContent.toString().contains("breaksOn=true"));
        assertTrue(outContent.toString().contains("breaksOn=false"));
        verify(strategy).getNextStop(elevator1.getId());
    }

    @Test
    public void shouldStopOnFloorIfNotRequestedOnCallback() {
        when(strategy.shouldStopAndRemove(elevator1.getId())).thenReturn(false);

        service.elevatorArrived(elevator1.getId(), 4);
        assertFalse(outContent.toString().contains("breaksOn=true"));
        assertFalse(outContent.toString().contains("breaksOn=false"));
    }

    @Test
    public void shouldCheckForNextStopAndGoToWaitingIfNone() {
        when(strategy.getNextStop(elevator1.getId())).thenReturn(empty());
        elevator1.setState(MOVING);

        service.elevatorArrived(elevator1.getId(), 4);

        assertTrue(outContent.toString().contains("Elevator1: Nothing to do, so Waiting..."));
        assertEquals(WAITING, elevator1.getState());
    }

    @Test
    public void shouldLockBreaksIfTheNextStopIsTheSameFloor() {
        when(strategy.getNextStop(elevator1.getId())).thenReturn(of(4)).thenReturn(empty());
        when(strategy.shouldStopAndRemove(elevator1.getId())).thenReturn(true);
        elevator1.setState(MOVING);

        service.elevatorArrived(elevator1.getId(), 4);

        assertTrue(outContent.toString().contains("Already at floor: 4"));
        assertTrue(outContent.toString().contains("breaksOn=true"));
        assertTrue(outContent.toString().contains("breaksOn=false"));
    }
}