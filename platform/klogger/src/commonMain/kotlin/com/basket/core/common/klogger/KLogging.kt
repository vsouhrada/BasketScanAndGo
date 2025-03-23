package com.basket.core.common.klogger

import io.github.oshai.kotlinlogging.KLogger

/**
 * Log exception and message to different levels.
 *
 * @param t cause
 */
fun KLogger.logMultiplexExpected(t: Throwable) {
    LoggingSupport.multiplexExpected(logger = this, t = t, message = t.message)
}

/**
 * Log exception and message to different levels.
 *
 * @param t cause
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logMultiplexExpected(t: Throwable? = null, message: () -> Any?,) {
    LoggingSupport.info(logger = this, message = message.invoke())
    t?.let {
        LoggingSupport.debug(logger = this, message = it.stackTraceToString())
    }
}

/**
 * Log exception and message to different levels.
 *
 * @param t cause
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logMultiplex(t: Throwable, message: () -> Any?,) {
    LoggingSupport.info(logger = this, message = message.invoke())
    LoggingSupport.debug(logger = this, message = t.stackTraceToString())
    LoggingSupport.error(logger = this, t = t)
}

/**
 * Log message with trace level.
 *
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logTrace(message: () -> Any?) {
    LoggingSupport.trace(logger = this, message = message.invoke())
}

/**
 * Log message with trace level.
 *
 * @param t cause
 * @param messages messages that will be concatenated to the resulting log message, any object with toString
 */
fun KLogger.logTrace(t: Throwable? = null, vararg messages: Any?,) {
    LoggingSupport.trace(logger = this, t = t, messages = messages)
}

/**
 * Log message with debug level.
 *
 * @param logger logger instance from calling class
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logDebug(message: () -> Any?) {
    LoggingSupport.debug(logger = this, message = message.invoke())
}

/**
 * Log message with debug level.
 *
 * @param t cause
 * @param messages messages that will be concatenated to the resulting log message, any object with toString
 */
fun KLogger.logDebug(t: Throwable? = null, vararg messages: Any?,) {
    LoggingSupport.debug(logger = this, t = t, messages = messages)
}

/**
 * Log at either DEBUG or INFO level, depending on the given flag value.
 *
 * @param isInfo the is info
 * @param message the message
 */
inline fun KLogger.logDebugOrInfo(isInfo: Boolean, message: () -> Any?,) {
    if (isInfo) {
        LoggingSupport.info(logger = this, message = message.invoke())
    } else {
        LoggingSupport.debug(logger = this, message = message.invoke())
    }
}

/**
 * Log message with info level.
 *
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logInfo(message: () -> Any?) {
    LoggingSupport.info(logger = this, message = message.invoke())
}

/**
 * Log message with info level.
 *
 * @param t cause
 * @param messages messages that will be concatenated to the resulting log message, any object with toString
 */
fun KLogger.logInfo(t: Throwable? = null, vararg messages: Any?,) {
    LoggingSupport.info(logger = this, t = t, messages = messages)
}

/**
 * Log message with warn level.
 *
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logWarn(message: () -> Any?) {
    LoggingSupport.warn(logger = this, message = message.invoke())
}

/**
 * Log message with warn level.
 *
 * @param t cause
 * @param messages messages that will be concatenated to the resulting log message, any object with toString
 */
fun KLogger.logWarn(t: Throwable? = null, vararg messages: Any?,) {
    LoggingSupport.warn(logger = this, t = t, messages = messages)
}

/**
 * Log message with error level.
 *
 * @param t the t
 * @param message message to be logged directly, any object with toString
 */
inline fun KLogger.logError(t: Throwable? = null, message: () -> Any?,) {
    LoggingSupport.error(logger = this, t = t, message = message.invoke())
}

/**
 * Log message with error level.
 *
 * @param t cause
 * @param messages messages that will be concatenated to the resulting log message, any object with toString
 */
fun KLogger.logError(t: Throwable? = null, vararg messages: Any?,) {
    LoggingSupport.error(logger = this, t = t, messages = messages)
}

/**
 * Macro method to log that a method has been entered.
 *
 * @param method The name of the method
 * @return The current system time (useful to measure durations)
 */
fun KLogger.logEnter(method: String): Long {
    return LoggingSupport.logEnter(logger = this, method = method)
}

/**
 * Macro method to log that a method has been entered.
 *
 * @param method The name of the method
 * @param args The arguments.
 * @return The current system time (useful to measure durations)
 */
fun KLogger.logEnter(method: String?, vararg args: Any?,): Long {
    return LoggingSupport.logEnter(logger = this, method = method, args = args)
}

/**
 * Macro method to log that a method has been left without a result.
 *
 * @param method The name of the method
 * @param start Start execution time of the method
 */
fun KLogger.logLeave(method: String, start: Long,) {
    LoggingSupport.logLeave(logger = this, method = method, start = start)
}

/**
 * Macro method to log that a method has been left with a result.
 *
 * @param <R> the generic type
 * @param method The name of the method
 * @param start Start execution time of the method
 * @param result The method result.
 * @return The result
 */
fun <R> KLogger.logLeaveWithResult(method: String, start: Long, result: R,): R {
    return LoggingSupport.logLeave(logger = this, method = method, start = start, result = result)
}

/**
 * Log duration of the code block (e.g. it could be function). It uses logEnter and logLeave.
 *
 * @param blockName It could be the name of the function
 * @param block The measured code or function
 * @see logEnter
 * @see logLeave
 */
inline fun KLogger.logDuration(blockName: String, block: () -> Any?,) {
    val start = LoggingSupport.logEnter(logger = this, method = blockName)
    block.invoke()
    LoggingSupport.logLeave(logger = this, method = blockName, start = start)
}

/**
 * Log duration of the code block (e.g. it could be function) and return result. It uses logEnter and logLeaveWithResult.
 *
 * @param blockName It could be the name of the function
 * @param block The measured code or function
 * @see logEnter
 * @see logLeaveWithResult
 * @return The result
 */
inline fun <R> KLogger.logDurationWithResult(blockName: String, block: () -> R,): R {
    val start = LoggingSupport.logEnter(logger = this, method = blockName)
    val result = block.invoke()
    return LoggingSupport.logLeave(logger = this, method = blockName, start = start, result = result)
}

/**
 * Log duration of the code block (e.g. it could be function) and return result. It uses logEnter and logLeaveWithResult.
 *
 * @param blockName It could be the name of the function
 * @param messages Messages that will be concatenated to the resulting log message, any object with toString
 * @param block The measured code or function
 * @see logEnter
 * @see logLeaveWithResult
 * @return The result
 */
inline fun <R> KLogger.logDurationWithResult(blockName: String, vararg messages: Any?, block: () -> R,): R {
    val start = LoggingSupport.logEnter(logger = this, method = blockName, args = messages)
    val result = block.invoke()
    return LoggingSupport.logLeave(logger = this, method = blockName, start = start, result = result)
}
