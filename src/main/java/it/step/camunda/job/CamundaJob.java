package it.step.camunda.job;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
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
    public Map<String,Object> testDemostration(ActivatedJob job, @VariablesAsType Map<String,Object> variables){
        log.info("Entering in testDemostration() variables : {}", variables);
        System.out.println(job.getVariablesAsMap());
        Boolean result = (Boolean) variables.get(AppConstants.PAYLOAD_KEY);
        variables.put(AppConstants.PAYLOAD_KEY, !result);
        log.info("Exiting from testDemostration() variables : {}", variables);
        return  variables;
    }
}
