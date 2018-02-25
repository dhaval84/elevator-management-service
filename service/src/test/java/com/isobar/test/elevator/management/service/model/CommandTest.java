/*
 * Copyright (c) Message4U Pty Ltd 2014-2018
 *
 * Except as otherwise permitted by the Copyright Act 1967 (Cth) (as amended from time to time) and/or any other
 * applicable copyright legislation, the material may not be reproduced in any format and in any way whatsoever
 * without the prior written consent of the copyright owner.
 */

package com.isobar.test.elevator.management.service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldBeAbleToDeSerializeToUpCommand() throws Exception {
        UpCommand expectedCommand = new UpCommand(4);

        assertEquals(expectedCommand, mapper.readValue(getFileString("/upCommand.json"), UpCommand.class));
    }

    @Test
    public void shouldBeAbleToDeSerializeToDownCommand() throws Exception {
        DownCommand expectedCommand = new DownCommand(4);

        assertEquals(expectedCommand, mapper.readValue(getFileString("/downCommand.json"), DownCommand.class));
    }

    @Test
    public void shouldBeAbleToDeSerializeToFloorCommand() throws Exception {
        FloorCommand expectedCommand = new FloorCommand(2, 4);

        assertEquals(expectedCommand, mapper.readValue(getFileString("/floorCommand.json"), FloorCommand.class));
    }

    @Test
    public void shouldBeAbleToDeSerializeToStopCommand() throws Exception {
        StopCommand expectedCommand = new StopCommand(1);

        assertEquals(expectedCommand, mapper.readValue(getFileString("/stopCommand.json"), StopCommand.class));
    }

    private String getFileString(String filePath) throws Exception {
        return IOUtils.toString(getClass().getResourceAsStream(filePath));
    }
}