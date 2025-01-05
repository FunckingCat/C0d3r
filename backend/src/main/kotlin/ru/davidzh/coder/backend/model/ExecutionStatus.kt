package ru.davidzh.coder.backend.model

enum class ExecutionStatus {
    /** Ожидает выполнения */
    PENDING,
    /** В процессе выполнения */
    RUNNING,
    /** Выполнено успешно */
    COMPLETED,
    /** Завершено с ошибкой */
    FAILED,
    /** Задача отменена */
    CANCELLED
}
