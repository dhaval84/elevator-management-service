
package com.isobar.test.elevator.management.service.routing;

import com.isobar.test.elevator.management.service.model.DestinationRoute;

import java.util.Optional;

public interface RoutingStrategy {

    void addStopToElevator(int elevatorId, int floor);
    void addRouteToAnyElevator(DestinationRoute destinationRoute);

    Optional<Integer> getNextStop(int elevatorId);
    boolean shouldStopAndRemove(int elevatorId);
}
