package ru.davidzh.coder.backend.util.extension

import java.util.*

fun String.toBase64(): String =
    Base64.getEncoder().encodeToString(this.toByteArray(Charsets.UTF_8))