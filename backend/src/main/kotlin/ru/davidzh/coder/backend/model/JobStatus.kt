package ru.davidzh.coder.backend.model

enum class JobStatus {
    /** Ожидает запуска */
    PENDING,
    /** Выполняется */
    RUNNING,
    /** Завершена */
    COMPLETED,
    /** Прервана */
    FAILED,
    /** Отменена */
    CANCELLED
}