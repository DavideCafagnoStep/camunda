package it.step.camunda.controller;

import io.camunda.zeebe.client.ZeebeClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.step.camunda.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Job Controller", description = "Controller for starting jobs")
public class JobController {

    private final ZeebeClient zeebe;


    @Operation(summary = "Start test Process",
            description = "process thath return true or false")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/gateway-test")
    public void activeGatewayTest(@RequestParam( defaultValue = "false") Boolean data) {
        log.info("Entering in activeGatewayTest() method");
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(AppConstants.TEST_GATEWAY_PROCESS_ID)
                .latestVersion()
                .variables(Map.of(AppConstants.PAYLOAD_KEY, data))
                .send()
                .join();
        log.info("Exiting from activeGatewayTest() method");
    }


    @Operation(summary = "Start test Process",
            description = "process thath return en possibly an Exception ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/error")
    public void activeErrorTest(@RequestParam( defaultValue = "true") Boolean error, @RequestParam( defaultValue = "false") Boolean data) {
        log.info("Entering in activeErrorTest() method");
        Map<String, Object> variables = new HashMap<>();
        variables.put(AppConstants.PAYLOAD_KEY,data);
        variables.put(AppConstants.ERROR_KEY,error);

        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(AppConstants.ERROR_PROCESS_ID)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
        log.info("Exiting from activeErrorTest() method");
    }

}