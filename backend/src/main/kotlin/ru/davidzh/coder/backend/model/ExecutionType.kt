package ru.davidzh.coder.backend.model

enum class ExecutionType {
    /** Выполнение по запросу */
    ON_DEMAND,
    /** Регулярное выполнение по расписанию */
    SCHEDULED,
    /** Асинхронный запуск */
    ASYNC,
    /** Запуск по событию */
    WEBHOOK
}