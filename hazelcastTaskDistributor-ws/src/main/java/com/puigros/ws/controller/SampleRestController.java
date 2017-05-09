package com.puigros.ws.controller;

import com.puigros.model.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is a sample rest controller class.
 * <p>
 * <p>
 *
 * @author crequena
 * @see <a href="https://agile.hotelbeds.com/confluence/display/EA/Reference+Architecture+-+API+resources+definition#ReferenceArchitecture-
 * APIresourcesdefinition-Examples</a>
 * @see
 * @since JDK1.8
 */
@Slf4j
@RestController
@RequestMapping(value = "/hazelcastTaskDistributor/1.0/")
@Api(value="Example System")
public class SampleRestController {

    @Autowired
    private SampleService sampleService;

    /**
     * Retrieve All Samples
     * @return All samples
     */

    @ApiOperation(
            tags = "SAMPLE",
            value = "Get a list of SampleDTO",
            notes = "The Samples endpoint returns a list of SampleDTO objects",
            response = Boolean.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/samples", produces = {MediaType.APPLICATION_JSON_VALUE}, 
                    headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<Boolean>> samplesGET() {

            List<Boolean> dtos = new ArrayList<>();

            return new ResponseEntity<List<Boolean>>(dtos, HttpStatus.OK);

    }


}
