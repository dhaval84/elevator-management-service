
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.Application;
import com.isobar.test.elevator.management.service.ElevatorService;
import com.isobar.test.elevator.management.service.model.*;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(DataProviderRunner.class)
public class CommandsControllerTest {

    private CommandsController controller;
    @Mock
    private ElevatorService service;

    @Before
    public void setup() {
        initMocks(this);
        controller = new CommandsController(service, new Application().elevatorMap());
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("validationScenarios")
    public void shouldTestValidations(Command command) {
        controller.commandReceived(command);
    }

    @DataProvider
    public static Object[][] validationScenarios() {
        return new Object[][] {
                {new UpCommand(7)},
                {new UpCommand(5)},
                {new DownCommand(-1)},
                {new DownCommand(0)},
                {new FloorCommand(1, -1)},
                {new FloorCommand(2, 6)},
                {new FloorCommand(3, 4)},
                {new StopCommand(3)}
        };
    }

    @Test
    public void shouldPassValidCommandToService() {
        UpCommand command = new UpCommand(3);
        controller.commandReceived(command);

        verify(service).processCommand(command);
    }
}