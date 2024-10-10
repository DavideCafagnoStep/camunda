package it.step.camunda;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//POSTMAN COLLECTION
//https://www.postman.com/camundateam/camunda-8-postman/collection/rbbwuqi/tasklist-public-api-rest?action=share&creator=11465105
@SpringBootApplication
@Deployment(resources = "classpath:/bpmn/**")
public class CamundaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaApplication.class, args);
	}

}
