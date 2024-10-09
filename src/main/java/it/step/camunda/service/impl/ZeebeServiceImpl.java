package it.step.camunda.service.impl;

import io.camunda.zeebe.client.ZeebeClient;
import it.step.camunda.service.ZeebeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ZeebeServiceImpl implements ZeebeService {

    private final ZeebeClient zeebe;
    @Override
    public void startProcess(String processId, Map<String, Object> variables) {
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(processId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
    }
}
