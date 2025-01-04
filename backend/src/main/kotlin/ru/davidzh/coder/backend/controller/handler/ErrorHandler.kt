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

@ControllerAdvice
class ErrorHandler {

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