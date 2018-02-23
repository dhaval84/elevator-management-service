package org.test.elevator.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElevatorFactoryTest {

	@Test
	public void testGetElevatorFacade() {
		ElevatorFacade facade = ElevatorFactory.getElevatorFacade(1, new ElevatorCallback() {
			@Override
			public void elevatorArrived(int floor) {
				
			}
		});
		
		ElevatorFacade facade2 = ElevatorFactory.getElevatorFacade(1, new ElevatorCallback() {
			@Override
			public void elevatorArrived(int floor) {
				
			}
		});
		
		assertSame(facade, facade2);
		
		ElevatorFacade facade3 = ElevatorFactory.getElevatorFacade(2, new ElevatorCallback() {
			@Override
			public void elevatorArrived(int floor) {
				
			}
		});
		
		assertNotSame(facade, facade3);
		
	}

}
