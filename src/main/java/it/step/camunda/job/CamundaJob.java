package it.step.camunda.job;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.model.bpmn.BpmnModelException;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import it.step.camunda.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CamundaJob {

    private final ZeebeClient zeebeClient;


    @JobWorker(type = "test-demostration")
    public Map<String, Object> testDemostration(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in testDemostration() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean result = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !result);
        log.info("Exiting from testDemostration() variables : {}", variables);
        return variables;
    }

    @JobWorker(type = "test-demostration-error")
    public Map<String, Object> testDemostrationError(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in testDemostrationError() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean result = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !result);
        Boolean error = (Boolean) variables.get(AppConstants.ERROR_KEY);
        if (error) {
            throw new ZeebeBpmnError(AppConstants.TEST_ERROR_CODE, "Error Occour", variables);
        }
        log.info("Exiting from testDemostrationError() variables : {}", variables);
        return variables;
    }
}
