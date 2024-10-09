package it.step.camunda.controller;

import io.camunda.zeebe.client.ZeebeClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.step.camunda.service.ZeebeService;
import it.step.camunda.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Job Controller", description = "Controller for starting jobs")
public class JobController {

    private final ZeebeService zeebe;

    @Operation(summary = "Start Test Process",
            description = "Process that only log the event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/basilar-test")
    public void activeBasilarTest() {
        log.info("Entering in activeBasilarTest() method");
        zeebe.startProcess(AppConstants.BASILAR_FLOW_PROCESS_ID, new LinkedHashMap<String,Object>());
        log.info("Exiting from activeBasilarTest() method");
    }


    @Operation(summary = "Start Test Process",
            description = "Process that reverse the payload value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/gateway-test")
    public void activeGatewayTest(@RequestParam( defaultValue = "false") Boolean payload) {
        log.info("Entering in activeGatewayTest() method");
        zeebe.startProcess(AppConstants.TEST_GATEWAY_PROCESS_ID,Map.of(AppConstants.PAYLOAD_KEY, payload));
        log.info("Exiting from activeGatewayTest() method");
    }


    @Operation(summary = "Start Error test Process",
            description = "Process that throw an Exception depending on error variable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/error")
    public void activeErrorTest(@RequestParam( defaultValue = "true") Boolean error, @RequestParam( defaultValue = "false") Boolean payload) {
        log.info("Entering in activeErrorTest() method");
        Map<String, Object> variables = new HashMap<>();
        variables.put(AppConstants.PAYLOAD_KEY,payload);
        variables.put(AppConstants.ERROR_KEY,error);
        zeebe.startProcess(AppConstants.ERROR_PROCESS_ID,variables);
        log.info("Exiting from activeErrorTest() method");
    }

}