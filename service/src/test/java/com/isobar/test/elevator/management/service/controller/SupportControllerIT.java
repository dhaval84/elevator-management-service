
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.model.ElevatorPublic;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

public class SupportControllerIT extends BaseControllerIT {

    private static final String SUPPORT_URL = HOST_URL + "/support/elevators";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeAbleToGetStatusOfElevators() {
        ResponseEntity<List<ElevatorPublic>> responseEntity = restTemplate.exchange(SUPPORT_URL, GET, null, TYPE_REFERENCE);

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    private static final ParameterizedTypeReference<List<ElevatorPublic>> TYPE_REFERENCE
            = new ParameterizedTypeReference<List<ElevatorPublic>>() {
    };
}
