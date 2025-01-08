package ru.davidzh.coder.backend.aop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.davidzh.coder.backend.aop.annotation.LogExecution

/**
 * Aspect for logging execution of methods annotated with `@LogExecution`.
 *
 * This class uses Aspect-Oriented Programming (AOP) to log method execution details, including method
 * arguments and result, for methods that are annotated with the `@LogExecution` annotation. The log
 * entries are captured before and after the method execution.
 *
 * @see LogExecution
 */
@Aspect
@Component
class RestLoggingAspect {

    /**
     * Logs method execution details (name, arguments, result) for methods annotated with `@LogExecution`.
     *
     * This method is executed around the annotated method, logging the method name, its arguments
     * before execution, and its result after execution. The logging is done at the `DEBUG` level.
     *
     * @param joinPoint The join point providing details of the intercepted method.
     * @param config The `@LogExecution` annotation containing configuration parameters (if any).
     * @return The result of the method execution.
     */
    @Around("@annotation(config)")
    fun logAround(joinPoint: ProceedingJoinPoint, config: LogExecution): Any? {
        val args = joinPoint.args
        val methodName = joinPoint.signature.name
        logger.debug(">>> {}() - {}", methodName, args.contentToString())
        val result = joinPoint.proceed()
        logger.debug("<<< {}() - {}", methodName, result)
        return result
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RestLoggingAspect::class.java)
    }

}