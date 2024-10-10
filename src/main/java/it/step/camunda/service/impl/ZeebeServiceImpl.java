package it.step.camunda.service.impl;

import io.camunda.zeebe.client.ZeebeClient;
import it.step.camunda.service.ZeebeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void startWithMessage(String messageName, String messageCorrelationKey, Map<String, Object> variables) {
        if (StringUtils.isEmpty(messageCorrelationKey)) {
            zeebe.newPublishMessageCommand()
                    .messageName(messageName)
                    .withoutCorrelationKey()
                    .variables(variables)
                    .send()
                    .join();
        } else {
            zeebe.newPublishMessageCommand()
                    .messageName(messageName)
                    .correlationKey(messageCorrelationKey)
                    .variables(variables)
                    .send()
                    .join();
        }

    }
}
