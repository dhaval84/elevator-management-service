
package com.isobar.test.elevator.management.service.routing;

import com.isobar.test.elevator.management.service.Application;
import com.isobar.test.elevator.management.service.model.DestinationRoute;
import com.isobar.test.elevator.management.service.model.Direction;
import com.isobar.test.elevator.management.service.model.Elevator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;
import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class PreAssignRequestCommandStrategyTest {

    private PreAssignRequestCommandStrategy strategy;
    private Map<Integer, Elevator> elevatorMap;
    private static Elevator elevator1;
    private static Elevator elevator2;

    @Before
    public void setup() {
        elevatorMap = new Application().elevatorMap();
        elevator1 = elevatorMap.values().stream().filter(elevator -> elevator.getId() == 1).findAny().get();
        elevator1.setFloor(3);
        elevator2 = elevatorMap.values().stream().filter(elevator -> elevator.getId() == 2).findAny().get();
        strategy = new PreAssignRequestCommandStrategy(elevatorMap);
    }

    @Test
    public void addingStopToElevatorSameAsElevator() {
        strategy.addStopToElevator(elevator1.getId(), 3);

        assertEquals(0, elevator1.getUpwardStopsList().size());
        assertEquals(0, elevator1.getDownwardStopsList().size());
    }

    @Test
    public void addingStopToElevatorWhichAlreadyExist() {
        elevator1.getUpwardStopsList().add(4);
        elevator1.getDownwardStopsList().add(2);

        strategy.addStopToElevator(elevator1.getId(), 4);
        strategy.addStopToElevator(elevator1.getId(), 2);

        assertEquals(1, elevator1.getUpwardStopsList().size());
        assertEquals(1, elevator1.getDownwardStopsList().size());
        assertEquals((Integer) 4, elevator1.getUpwardStopsList().iterator().next());
        assertEquals((Integer) 2, elevator1.getDownwardStopsList().iterator().next());
    }

    @Test
    public void shouldAddStopsToUpwardDownwardSetAutomaticallyWhenLiftIsMovingUp() {
        elevator1.setDirection(UP);
        elevator1.setFloor(4);

        strategy.addStopToElevator(elevator1.getId(), 3);
        strategy.addStopToElevator(elevator1.getId(), 5);
        strategy.addStopToElevator(elevator1.getId(), 1);

        assertEquals(1, elevator1.getUpwardStopsList().size());
        assertEquals(2, elevator1.getDownwardStopsList().size());
        assertEquals((Integer) 5, elevator1.getUpwardStopsList().iterator().next());
        Iterator<Integer> iterator = elevator1.getDownwardStopsList().iterator();
        assertEquals((Integer) 3, iterator.next());
        assertEquals((Integer) 1, iterator.next());
    }

    @Test
    public void shouldAddStopsToUpwardDownwardSetAutomaticallyWhenLiftIsMovingDown() {
        elevator1.setDirection(DOWN);
        elevator1.setFloor(2);

        strategy.addStopToElevator(elevator1.getId(), 3);
        strategy.addStopToElevator(elevator1.getId(), 5);
        strategy.addStopToElevator(elevator1.getId(), 1);

        assertEquals(1, elevator1.getDownwardStopsList().size());
        assertEquals(2, elevator1.getUpwardStopsList().size());
        assertEquals((Integer) 1, elevator1.getDownwardStopsList().iterator().next());
        Iterator<Integer> iterator = elevator1.getUpwardStopsList().iterator();
        assertEquals((Integer) 3, iterator.next());
        assertEquals((Integer) 5, iterator.next());
    }

    @Test
    @UseDataProvider("nextStopUpwardScenarios")
    public void shouldBeAbleToFindCorrectNextStopOnWayUp(Set<Integer> upwardStops, Set<Integer> downwardStops,
                                                         int elevatorFloor, Direction direction, int expectedStop) {
        elevator1.setDirection(direction);
        elevator1.setFloor(elevatorFloor);
        elevator1.getUpwardStopsList().addAll(upwardStops);
        elevator1.getDownwardStopsList().addAll(downwardStops);

        int nextStop = strategy.getNextStop(elevator1.getId()).get();

        assertEquals(expectedStop, nextStop);
    }

    @DataProvider
    public static Object[][] nextStopUpwardScenarios() {
        return new Object[][] {
                {of(5), of(1, 2), 3, UP, 5},        // In same direction
                {of(1, 2), of(1, 2), 3, UP, 2},     // In opposite direction
                {of(1, 2), of(), 3, UP, 1},         // In same direction but already passed
                {of(), of(1, 5), 3, UP, 5},
                {of(0, 5), of(2, 3), 4, DOWN, 3},   // In same direction
                {of(2, 5), of(4), 3, DOWN, 2},      // In opposite direction
                {of(), of(2, 3), 1, DOWN, 3}        // In same direction but already passed
        };
    }

    @Test
    @UseDataProvider("shouldStopAndRemoveScenarios")
    public void testShouldStopAndRemove(Set<Integer> upwardStops, Set<Integer> downwardStops, int elevatorFloor, Direction direction,
                                        boolean expectedShouldStop, Set<Integer> expectedUpwardStops, Set<Integer> expectedDownwardStops) {

        elevator1.setDirection(direction);
        elevator1.setFloor(elevatorFloor);
        elevator1.getUpwardStopsList().addAll(upwardStops);
        elevator1.getDownwardStopsList().addAll(downwardStops);

        boolean shouldStop = strategy.shouldStopAndRemove(elevator1.getId());

        assertEquals(expectedShouldStop, shouldStop);
        assertEquals(expectedUpwardStops.size(), elevator1.getUpwardStopsList().size());
        assertEquals(expectedDownwardStops.size(), elevator1.getDownwardStopsList().size());
    }

    @DataProvider
    public static Object[][] shouldStopAndRemoveScenarios() {
        return new Object[][] {
                {of(3, 5), of(1, 2), 3, UP, true, of(5), of(1, 2)},
                {of(3, 5), of(1, 2), 2, UP, false, of(3, 5), of(1, 2)},
                {of(3, 5), of(1, 2), 2, DOWN, true, of(3, 5), of(1)},
                {of(3, 5), of(1, 2), 3, DOWN, false, of(3, 5), of(1, 2)},
        };
    }

    @Test
    @UseDataProvider("hopsRemainingScenarios")
    public void shouldCheckIfNumberOfHopsCalculatedProperly(Set<Integer> upwardStops, Set<Integer> downwardStops,
                                                               Direction direction, int floor, DestinationRoute destinationRoute,
                                                               long numberOfHops) {
        elevator1.setDirection(direction);
        elevator1.setFloor(floor);
        elevator1.getUpwardStopsList().addAll(upwardStops);
        elevator1.getDownwardStopsList().addAll(downwardStops);

        long hopsRemaining = strategy.hopsRemaining(elevator1, destinationRoute);

        assertEquals(numberOfHops, hopsRemaining);
    }

    @DataProvider
    public static Object[][] hopsRemainingScenarios() {
        return new Object[][] {
                {of(2, 5), of(3, 2), UP, 3, new DestinationRoute(UP, 5), 0},        // Already present in same direction
                {of(2, 5), of(4, 2), UP, 3, new DestinationRoute(DOWN, 4), 0},      // Already present in opposite direction
                {of(1, 4), of(4, 2), UP, 2, new DestinationRoute(UP, 5), 1},        // In Same direction
                {of(1, 5), of(4, 2), UP, 2, new DestinationRoute(DOWN, 3), 2},      // In Opposite direction
                {of(1, 5), of(4, 2), UP, 3, new DestinationRoute(UP, 2), 4},        // In same direction, but already passed

                {of(2, 5), of(3, 2), DOWN, 3, new DestinationRoute(UP, 5), 0},      // Already present in same direction
                {of(2, 5), of(4, 2), DOWN, 3, new DestinationRoute(DOWN, 4), 0},    // Already present in opposite direction
                {of(1, 4), of(4, 2), DOWN, 3, new DestinationRoute(DOWN, 1), 1},    // In Same direction
                {of(1, 5), of(4, 2), DOWN, 3, new DestinationRoute(UP, 3), 2},      // In Opposite direction
                {of(1, 5), of(4, 1), DOWN, 2, new DestinationRoute(DOWN, 3), 4},    // In same direction, but already passed
        };
    }

    @Test
    public void shouldTestThatElevatorWithMinHopsIsSelectedInUpwardDirection() {
        elevator1.setDirection(UP);
        elevator1.setFloor(3);
        elevator1.getUpwardStopsList().addAll(of(1, 5));
        elevator1.getDownwardStopsList().addAll(of(4, 2));

        elevator2.setDirection(UP);
        elevator2.setFloor(4);
        elevator2.getUpwardStopsList().addAll(of(1));
        elevator2.getDownwardStopsList().addAll(of(4, 2));

        strategy.addRouteToAnyElevator(new DestinationRoute(UP, 2));

        assertEquals(2, elevator1.getUpwardStopsList().size());
        assertEquals(2, elevator2.getUpwardStopsList().size());
    }

    @Test
    public void shouldTestThatElevatorWithMinHopsIsSelectedInDownwardDirection() {
        elevator1.setDirection(UP);
        elevator1.setFloor(3);
        elevator1.getUpwardStopsList().addAll(of(1, 5));
        elevator1.getDownwardStopsList().addAll(of(4, 1));

        elevator2.setDirection(UP);
        elevator2.setFloor(4);
        elevator2.getUpwardStopsList().addAll(of(1, 5));
        elevator2.getDownwardStopsList().addAll(of(4, 3));

        strategy.addRouteToAnyElevator(new DestinationRoute(DOWN, 2));

        assertEquals(3, elevator1.getDownwardStopsList().size());
        assertEquals(2, elevator2.getDownwardStopsList().size());
    }
}