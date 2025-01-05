package ru.davidzh.coder.backend.aop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import java.util.*

@Aspect
@Component
class RestLoggingAspect {

    @Around("@annotation(config)")
    fun logAround(joinPoint: ProceedingJoinPoint, config: LogExecution): Any? {
        val args = joinPoint.args
        val methodName = joinPoint.signature.name
        logger.debug(">>> {}() - {}", methodName, Arrays.toString(args))
        val result = joinPoint.proceed()
        logger.debug("<<< {}() - {}", methodName, result)
        return result
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RestLoggingAspect::class.java)
    }

}