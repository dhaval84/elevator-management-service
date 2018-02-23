
package com.isobar.test.elevator.management.service.controller;

import com.isobar.test.elevator.management.service.model.BaseCommand;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/commands")
@RestController
public class CommandsController {

    @RequestMapping(method = POST)
    @ResponseStatus(ACCEPTED)
    public void commandReceived(@RequestBody BaseCommand command) {
        System.out.println("Command Received: " + command);
    }
}
