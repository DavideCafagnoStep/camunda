package it.step.camunda.service;

import java.util.Map;

public interface ZeebeService {

    public void startProcess(String processId, Map<String,Object> variables);
}
