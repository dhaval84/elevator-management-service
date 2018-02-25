
package com.isobar.test.elevator.management.service.routing;

import com.google.common.annotations.VisibleForTesting;
import com.isobar.test.elevator.management.service.model.DestinationRoute;
import com.isobar.test.elevator.management.service.model.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import static com.isobar.test.elevator.management.service.model.Direction.DOWN;
import static com.isobar.test.elevator.management.service.model.Direction.UP;
import static java.util.Optional.empty;

@Service
public class PreAssignRequestCommandStrategy implements RoutingStrategy {

    private final Map<Integer, Elevator> elevatorMap;

    @Autowired
    public PreAssignRequestCommandStrategy(Map<Integer, Elevator> elevatorMap) {
        this.elevatorMap = elevatorMap;
    }

    @Override
    public synchronized void addRouteToAnyElevator(DestinationRoute destinationRoute) {
        Optional<Elevator> elevatorWithMinHops = elevatorMap.values().stream()
                .min(Comparator.comparing(elevator -> hopsRemaining(elevator, destinationRoute)));
        if (elevatorWithMinHops.isPresent()) {
            switch (destinationRoute.getDirection()) {
                case UP:
                    elevatorWithMinHops.get().getUpwardStopsList().add(destinationRoute.getFloor());
                    break;
                case DOWN:
                    elevatorWithMinHops.get().getDownwardStopsList().add(destinationRoute.getFloor());
                    break;
            }
        }
    }

    @Override
    public synchronized void addStopToElevator(int elevatorId, int floor) {
        Elevator elevator = elevatorMap.get(elevatorId);

        if (floor == elevator.getFloor()) {
            // Nothing to do already on the same floor
            System.out.println("Already on floor: " + floor);
            return;
        }

        switch (elevator.getDirection()) {
            case UP:
                // Stops collection is a set so no problem if we are trying to add stop which is already there
                if (floor > elevator.getFloor()) {
                    elevator.getUpwardStopsList().add(floor);
                } else {
                    elevator.getDownwardStopsList().add(floor);
                }
                break;
            case DOWN:
                if (floor < elevator.getFloor()) {
                    elevator.getDownwardStopsList().add(floor);
                } else {
                    elevator.getUpwardStopsList().add(floor);
                }
        }
    }

    @Override
    public Optional<Integer> getNextStop(int elevatorId) {
        Elevator elevator = elevatorMap.get(elevatorId);
        Optional<Integer> nextStop = empty();
        switch (elevator.getDirection()) {
            case UP:
                synchronized (elevator.getUpwardStopsList()) {
                    nextStop = elevator.getUpwardStopsList().stream()
                            .filter(routeFloor -> routeFloor > elevator.getFloor())
                            .findFirst();

                    if (nextStop.isPresent()) {
                        return nextStop;
                    }
                }
                synchronized (elevator.getDownwardStopsList()) {
                    nextStop = elevator.getDownwardStopsList().stream().findFirst();
                    if (nextStop.isPresent()) {
                        elevator.setDirection(DOWN);
                        return nextStop;
                    }
                }
                synchronized (elevator.getUpwardStopsList()) {
                    return elevator.getUpwardStopsList().stream().findFirst();
                }
            case DOWN:
                synchronized (elevator.getDownwardStopsList()) {
                    nextStop = elevator.getDownwardStopsList().stream()
                            .filter(routeFloor -> routeFloor < elevator.getFloor())
                            .findFirst();

                    if (nextStop.isPresent()) {
                        return nextStop;
                    }
                }
                synchronized (elevator.getUpwardStopsList()) {
                    nextStop = elevator.getUpwardStopsList().stream().findFirst();
                    if (nextStop.isPresent()) {
                        elevator.setDirection(UP);
                        return nextStop;
                    }
                }
                synchronized (elevator.getDownwardStopsList()) {
                    return elevator.getDownwardStopsList().stream().findFirst();
                }
        }
        return nextStop;
    }

    @Override
    public boolean shouldStopAndRemove(int elevatorId) {
        Elevator elevator = elevatorMap.get(elevatorId);

        boolean shouldStop;
        switch (elevator.getDirection()) {
            case UP:
                synchronized (elevator.getUpwardStopsList()) {
                    shouldStop = elevator.getUpwardStopsList().contains(elevator.getFloor());
                    if (shouldStop) {
                        elevator.getUpwardStopsList().remove(elevator.getFloor());
                    }
                }
                break;
            case DOWN:
                synchronized (elevator.getDownwardStopsList()) {
                    shouldStop = elevator.getDownwardStopsList().contains(elevator.getFloor());
                    if (shouldStop) {
                        elevator.getDownwardStopsList().remove(elevator.getFloor());
                    }
                }
                break;
            default:
                return false;
        }
        return shouldStop;
    }

    @VisibleForTesting
    long hopsRemaining(Elevator elevator, DestinationRoute destinationRoute) {
        switch (elevator.getDirection()) {
            case UP:
                if (destinationRoute.getDirection().equals(UP)) {
                    if (elevator.getUpwardStopsList().contains(destinationRoute.getFloor())) {
                        // The floor is already present in the list for the lift
                        return 0;
                    }
                    long hops;
                    if (destinationRoute.getFloor() > elevator.getFloor()) {
                        synchronized (elevator.getUpwardStopsList()) {
                            hops = elevator.getUpwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor > elevator.getFloor() && routeFloor < destinationRoute.getFloor()))
                                    .count();
                        }
                    } else {
                        synchronized (elevator.getUpwardStopsList()) {
                            // Hops remaining in upward direction
                            hops = elevator.getUpwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor > elevator.getFloor()))
                                    .count();

                            // Hops in downward direction
                            hops += elevator.getDownwardStopsList().size();

                            //Hops in upward direction after the cycle
                            hops += elevator.getUpwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor < destinationRoute.getFloor()))
                                    .count();
                        }
                    }
                    return hops;
                } else {
                    if (elevator.getDownwardStopsList().contains(destinationRoute.getFloor())) {
                        // The floor is already present in the list for the lift
                        return 0;
                    }
                    long hops;
                    synchronized (elevator.getUpwardStopsList()) {
                        // Hops remaining in upward direction
                        hops = elevator.getUpwardStopsList().stream()
                                .filter(routeFloor -> (routeFloor > elevator.getFloor()))
                                .count();

                        // Hops in downward direction
                        hops += elevator.getDownwardStopsList().stream()
                                .filter(routeFloor -> (routeFloor > destinationRoute.getFloor()))
                                .count();
                    }
                    return hops;
                }
            case DOWN:
                if (destinationRoute.getDirection().equals(DOWN)) {
                    if (elevator.getDownwardStopsList().contains(destinationRoute.getFloor())) {
                        // The floor is already present in the list for the lift
                        return 0;
                    }
                    long hops;
                    if (destinationRoute.getFloor() < elevator.getFloor()) {
                        synchronized (elevator.getDownwardStopsList()) {
                            hops = elevator.getDownwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor < elevator.getFloor() && routeFloor > destinationRoute.getFloor()))
                                    .count();
                        }
                    } else {
                        synchronized (elevator.getDownwardStopsList()) {
                            // Hops remaining in upward direction
                            hops = elevator.getDownwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor < elevator.getFloor()))
                                    .count();

                            // Hops in downward direction
                            hops += elevator.getUpwardStopsList().size();

                            //Hops in upward direction after the cycle
                            hops += elevator.getDownwardStopsList().stream()
                                    .filter(routeFloor -> (routeFloor > destinationRoute.getFloor()))
                                    .count();
                        }
                    }
                    return hops;
                } else {
                    if (elevator.getUpwardStopsList().contains(destinationRoute.getFloor())) {
                        // The floor is already present in the list for the lift
                        return 0;
                    }
                    long hops;
                    synchronized (elevator.getDownwardStopsList()) {
                        // Hops remaining in upward direction
                        hops = elevator.getDownwardStopsList().stream()
                                .filter(routeFloor -> (routeFloor < elevator.getFloor()))
                                .count();

                        // Hops in downward direction
                        hops += elevator.getUpwardStopsList().stream()
                                .filter(routeFloor -> (routeFloor < destinationRoute.getFloor()))
                                .count();
                    }
                    return hops;
                }
        }
        return 0;
    }
}
