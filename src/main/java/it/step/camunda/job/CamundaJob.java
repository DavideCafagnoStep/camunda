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



    @JobWorker(type = "basilar-test")
    public Map<String, Object> basilarTest(@VariablesAsType Map<String, Object> variables) {
        log.info("Entering in basilarTest() variables : {}", variables);
        log.info("Exiting from basilarTest()");
        return variables;
    }

    @JobWorker(type = "gateway-test")
    public Map<String, Object> gatewayTest(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in gatewayTest() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean payload = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !payload);
        log.info("Exiting from gatewayTest() variables : {}", variables);
        return variables;
    }

    @JobWorker(type = "handle-error-test")
    public Map<String, Object> handleErrorTest(ActivatedJob job, @VariablesAsType Map<String, Object> variables) {
        log.info("Entering in handleErrorTest() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean payload = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !payload);
        Boolean error = (Boolean) variables.get(AppConstants.ERROR_KEY);
        if (error) {
            throw new ZeebeBpmnError(AppConstants.TEST_ERROR_CODE_01, "Error!", variables);
        }
        log.info("Exiting from handleErrorTest() variables : {}", variables);
        return variables;
    }
}
