package ru.davidzh.coder.backend.aop.annotation

/**
 * Annotation to enable logging of method execution details.
 *
 * This annotation, when applied to a method, triggers logging of the method's arguments before execution
 * and its result after execution. It is intended for use with aspects (such as `RestLoggingAspect`) that
 * intercept methods annotated with `@LogExecution` to log execution details at runtime.
 *
 * The logging is done at the `DEBUG` level by default.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogExecution()
