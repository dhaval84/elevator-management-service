
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.model.FloorCommand;
import com.isobar.test.elevator.management.service.model.UpCommand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CommandsControllerIT extends BaseControllerIT {

    private static final String COMMANDS_URL = HOST_URL + "/commands";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeAbleToSubmitUpCommand() {

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(COMMANDS_URL, new UpCommand(2), String.class);

        assertEquals(ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldBeAbleToSubmitFloorCommand() {

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(COMMANDS_URL, new FloorCommand(1, 2), String.class);

        assertEquals(ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldBadRequestIfInputIsNotValid() {

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(COMMANDS_URL, new FloorCommand(3, 2), String.class);

        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }
}
