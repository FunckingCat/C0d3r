package ru.davidzh.coder.backend

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * The main entry point for the Backend application.
 *
 * The application will be initialized and started via the [main] function.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@OpenAPIDefinition(
	info = Info(
		title = "Lambda platform API",
		version = "1.0.0",
		description = "This API provides functionalities for interaction with Lambda platform APIs."
	)
	// You can also add servers, security definitions etc. here
	// servers = @Server(...)
	// security = @SecurityRequirement(...)
)
class BackendApplication

/**
 * The main method to run the Spring Boot application.
 *
 * This method boots up the Spring Boot application by calling [runApplication], passing in the
 * arguments from the command line. It initializes the application context and starts the backend
 * services with the configurations specified in [BackendApplication].
 *
 * @param args command line arguments passed to the application at startup.
 */
fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}

