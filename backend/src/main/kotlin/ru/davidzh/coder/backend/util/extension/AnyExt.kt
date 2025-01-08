package ru.davidzh.coder.backend.util.extension

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Extension function to convert an object into a ResponseEntity with the specified HTTP status.
 *
 * This function takes the current object (`Any`) and wraps it in a ResponseEntity with the provided
 * HTTP status. If no status is provided, the default status is HTTP 200 OK.
 *
 * @param status the HTTP status to set for the ResponseEntity. Defaults to `HttpStatus.OK`.
 * @return a ResponseEntity containing the current object as the body and the specified status.
 */
fun Any.asResponseEntity(status: HttpStatus = HttpStatus.OK):
        ResponseEntity<Any> = ResponseEntity.status(status).body(this)