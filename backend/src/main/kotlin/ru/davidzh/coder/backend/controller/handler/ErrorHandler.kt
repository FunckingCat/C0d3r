package ru.davidzh.coder.backend.controller.handler

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.exception.ExceptionUtils.getRootCause
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.davidzh.coder.backend.controller.model.RestError
import ru.davidzh.coder.backend.util.extension.asResponseEntity

/**
 * Global exception handler for handling uncaught exceptions in the application.
 *
 * This class uses the `@ControllerAdvice` annotation to handle exceptions thrown
 * across the application. It provides centralized exception handling for REST controllers
 * and formats the error response in a structured format.
 */
@ControllerAdvice
class ErrorHandler {

    /**
     * Handles generic exceptions and returns an appropriate error response.
     *
     * This method is triggered for any unhandled exceptions thrown during the processing
     * of a request. It logs the exception and returns a standardized error response
     * with a status code of 500 (Internal Server Error).
     *
     * @param req The HttpServletRequest, used for logging the method and URI of the request.
     * @param e The exception that was thrown during the request processing.
     * @return A ResponseEntity containing the error details in the form of a [RestError].
     */
    @ExceptionHandler(Exception::class)
    fun handleException(req: HttpServletRequest, e: Exception): ResponseEntity<Any> {
        log.error("${req.method} ${req.requestURI} threw Exception ${e.javaClass} ${e.message}", e)
        return RestError(
            500,
            "Fatal Error",
            getRootCause(e)?.message ?: "Fatal Error",
        ).asResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val log = LoggerFactory.getLogger(ErrorHandler::class.java)
    }

}