package ru.davidzh.coder.backend

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

