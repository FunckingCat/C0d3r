package ru.davidzh.coder.backend.util.extension

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun Any.asResponseEntity(status: HttpStatus = HttpStatus.OK):
        ResponseEntity<Any> = ResponseEntity.status(status).body(this)