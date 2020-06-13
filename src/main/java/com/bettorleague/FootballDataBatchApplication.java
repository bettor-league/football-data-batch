package com.bettorleague;

import com.bettorleague.microservice.common.CommonMicroservice;
import com.bettorleague.microservice.mongo.MongoMicroservice;
import com.bettorleague.microservice.swagger.SwaggerMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MongoMicroservice
@CommonMicroservice
@SwaggerMicroservice
@EnableBatchProcessing
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Football Data Batch API", version = "1.0", description = "Documentation Football Data Batch API v1.0"))
public class FootballDataBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(FootballDataBatchApplication.class, args);
	}
}
