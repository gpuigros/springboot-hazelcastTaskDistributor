package com.puigros.ws.controller;

import com.puigros.service.TaskDistributorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/hazelcastTaskDistributor/1.0/")
@Api(value="HazelcastTaskDistributor System")
public class RestController {

    @Autowired
    private TaskDistributorService service;

    @ApiOperation(
            tags = "Hazelcast",
            value = "Start tasks",
            notes = "Start tasks",
            response = Boolean.class
            //responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/execute", produces = {MediaType.APPLICATION_JSON_VALUE},
                    headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Boolean> execute(
            @ApiParam(value = "tasks", required = true) @RequestParam(value = "tasks") Integer tasks
    ) {
            log.info("In");
            service.runTasksCreation(tasks);
            log.info("End");
            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

    }
    @ApiOperation(
            tags = "Hazelcast",
            value = "Consume tasks",
            notes = "Consume tasks",
            response = Boolean.class
            //responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/consume", produces = {MediaType.APPLICATION_JSON_VALUE},
            headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Boolean> consume() {
        log.info("In");
        service.runTasksConsumer();
        log.info("End");
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

    }

}
