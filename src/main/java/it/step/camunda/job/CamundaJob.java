package it.step.camunda.job;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
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


    @JobWorker(type = "test-demonstration")
    public Map<String, Object> testDemonstration(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in testDemonstration() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean payload = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !payload);
        log.info("Exiting from testDemonstration() variables : {}", variables);
        return variables;
    }

    @JobWorker(type = "test-demonstration-error")
    public Map<String, Object> testDemonstrationError(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in testDemonstrationError() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean payload = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !payload);
        Boolean error = (Boolean) variables.get(AppConstants.ERROR_KEY);
        if (error) {
            throw new ZeebeBpmnError(AppConstants.TEST_ERROR_CODE_01, "Error!", variables);
        }
        log.info("Exiting from testDemonstrationError() variables : {}", variables);
        return variables;
    }
}
