package com.basket.core.common.klogger

import io.github.oshai.kotlinlogging.KLogger
import kotlinx.datetime.Clock

private val LOGGER = getKLogger {}

object LoggingSupport {
    private val passwordBlackList = arrayOf("password=", "password ")

    var filterPasswords: Boolean = true

    private const val LOGGED_NULL = "Ll"
    private const val LOGGED_ITEMS_DELIMITER = ' '
    private const val MAX_ARRAY_LENGTH_LOGGED = 5
    private const val MAX_ARRAY_RECURSIVE_DEPTH = 13 // 3 x 3 array has max. recursive depth 12

    /**
     * Log exception and message to different levels.
     *
     * @param logger logger instance from calling class
     * @param t cause
     */
    fun multiplexExpected(
        logger: KLogger,
        t: Throwable,
    ) {
        multiplexExpected(logger, t, t.message)
    }

    /**
     * Log exception and message to different levels.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param message message to be logged directly, any object with toString
     */
    fun multiplexExpected(
        logger: KLogger,
        t: Throwable?,
        message: Any?,
    ) {
        info(logger, message)
        t?.let {
            debug(logger, it.stackTraceToString())
        }
    }

    /**
     * Log exception and message to different levels.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param message message to be logged directly, any object with toString
     */
    fun multiplex(
        logger: KLogger,
        t: Throwable,
        message: Any?,
    ) {
        info(logger, message)
        debug(logger, t.stackTraceToString())
        error(logger, t)
    }

    /**
     * Log exception and message to different levels.
     *
     * @param logger logger instance from calling class
     * @param t cause
     */
    fun multiplex(
        logger: KLogger,
        t: Throwable,
    ) {
        multiplex(logger, t, t.message)
    }

    /**
     * Log message with info level.
     *
     * @param logger logger instance from calling class
     * @param message message to be logged directly, any object with toString
     */
    fun info(
        logger: KLogger,
        message: Any?,
    ) {
        val s = simplyAppendMessage(logger, message)
        logger.info { sanitizeLogMessage(s) }
    }

    /**
     * Log message with info level.
     *
     * @param logger logger instance from calling class
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun info(
        logger: KLogger,
        vararg messages: Any?,
    ) {
        info(logger, null, *messages)
    }

    /**
     * Log message with info level.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun info(
        logger: KLogger,
        t: Throwable?,
        vararg messages: Any?,
    ) {
        val s = simplyAppendMessages(logger, messages)
        logger.info(t) { sanitizeLogMessage(s) }
    }

    /**
     * Log message with debug level.
     *
     * @param logger logger instance from calling class
     * @param message message to be logged directly, any object with toString
     */
    fun debug(
        logger: KLogger,
        message: Any?,
    ) {
        val s = simplyAppendMessage(logger, message)
        logger.debug { sanitizeLogMessage(s) }
    }

    /**
     * Log message with debug level.
     *
     * @param logger logger instance from calling class
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun debug(
        logger: KLogger,
        vararg messages: Any?,
    ) {
        debug(logger, null, *messages)
    }

    /**
     * Log message with debug level.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun debug(
        logger: KLogger,
        t: Throwable?,
        vararg messages: Any?,
    ) {
        val s = simplyAppendMessages(logger, messages)
        logger.debug(t) { sanitizeLogMessage(s) }
    }

    /**
     * Log message with trace level.
     *
     * @param logger logger instance from calling class
     * @param message message to be logged directly, any object with toString
     */
    fun trace(
        logger: KLogger,
        message: Any?,
    ) {
        val s = simplyAppendMessage(logger, message)
        logger.trace { sanitizeLogMessage(s) }
    }

    /**
     * Log message with trace level.
     *
     * @param logger logger instance from calling class
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun trace(
        logger: KLogger,
        vararg messages: Any?,
    ) {
        trace(logger, null, *messages)
    }

    /**
     * Log message with trace level.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun trace(
        logger: KLogger,
        t: Throwable?,
        vararg messages: Any?,
    ) {
        val s = simplyAppendMessages(logger, messages)
        logger.trace(t) { sanitizeLogMessage(s) }
    }

    /**
     * Log exception message and stack trace as ERROR.
     *
     * @param logger logger instance from calling class
     * @param throwable the throwable
     */
    fun error(
        logger: KLogger,
        throwable: Throwable,
    ) {
        val s = simplyAppendMessage(logger, throwable.message)
        logger.error { sanitizeLogMessage(s) }
    }

    /**
     * Log message with error level.
     *
     * @param logger logger instance from calling class
     * @param message message to be logged directly, any object (beside throwable) with toString
     */
    fun error(
        logger: KLogger,
        message: Any?,
    ) {
        val s = simplyAppendMessage(logger, message)
        logger.error { sanitizeLogMessage(s) }
    }

    /**
     * Log message with error level.
     *
     * @param logger logger instance from calling class
     * @param t the t
     * @param message message to be logged directly, any object with toString
     */
    fun error(
        logger: KLogger,
        t: Throwable?,
        message: Any?,
    ) {
        logger.error(t) { message.toString() }
    }

    /**
     * Log message with error level.
     *
     * @param logger logger instance from calling class
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun error(
        logger: KLogger,
        vararg messages: Any?,
    ) {
        error(logger, null, *messages)
    }

    /**
     * Log message with error level.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun error(
        logger: KLogger,
        t: Throwable?,
        vararg messages: Any?,
    ) {
        val s = simplyAppendMessages(logger, messages)
        logger.error(t) { sanitizeLogMessage(s) }
    }

    /**
     * Log message with warn level.
     *
     * @param logger logger instance from calling class
     * @param message message to be logged directly, any object with toString
     */
    fun warn(
        logger: KLogger,
        message: Any?,
    ) {
        val s = simplyAppendMessage(logger, message)
        logger.warn { sanitizeLogMessage(s) }
    }

    /**
     * Log message with warn level.
     *
     * @param logger logger instance from calling class
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun warn(
        logger: KLogger,
        vararg messages: Any?,
    ) {
        warn(logger, null, *messages)
    }

    /**
     * Log message with warn level.
     *
     * @param logger logger instance from calling class
     * @param t cause
     * @param messages messages that will be concatenated to the resulting log message, any object with toString
     */
    fun warn(
        logger: KLogger,
        t: Throwable?,
        vararg messages: Any?,
    ) {
        val s = simplyAppendMessages(logger, messages)
        logger.warn(t) { sanitizeLogMessage(s) }
    }

    private fun simplyAppendMessage(
        logger: KLogger,
        message: Any?,
    ): String {
        var sb = StringBuilder()
        simplyAppend(sb, message, false, 0)
        if (checkIsPasswordString(logger, sb)) {
            sb = StringBuilder("** Log message contains password **")
        }
        return sb.toString()
    }

    /**
     * Do the concatenation of given objects using their toString method.
     *
     * @param messages
     * @return appended string
     */
    private fun simplyAppendMessages(
        logger: KLogger,
        vararg messages: Any?,
    ): String {
        var sb = StringBuilder()
        for (message in messages) {
            simplyAppend(sb, message, false, 0)
        }
        if (checkIsPasswordString(logger, sb)) {
            sb = StringBuilder("** Log message contains password **")
        }
        return sb.toString()
    }

    private fun simplyAppend(
        sb: StringBuilder,
        message: Any?,
        logNull: Boolean,
        depth: Int,
    ) {
        var depth = depth
        if (message != null) {
            if (!isArray(message)) {
                try {
                    sb.append(message.toString())
                } catch (e: Exception) {
                    error(
                        LOGGER,
                        e,
                        "Please check toString() implementation of class " +
                            message::class.simpleName +
                            ", which throws exception.",
                    )
                    sb.append(LOGGED_NULL)
                }
            } else {
                if (depth++ == MAX_ARRAY_RECURSIVE_DEPTH) {
                    sb.append("....]").append(LOGGED_ITEMS_DELIMITER)
                    return
                }
                when (message) {
                    is Array<*> -> {
                        val msgArray = message
                        sb.append('[')
                        var i = 0
                        while (i < msgArray.size && i < MAX_ARRAY_LENGTH_LOGGED) {
                            if (depth <= MAX_ARRAY_RECURSIVE_DEPTH) {
                                simplyAppend(sb, msgArray[i], true, depth)
                            }
                            i++
                        }
                        if (MAX_ARRAY_LENGTH_LOGGED < msgArray.size) {
                            sb.append("...(").append(msgArray.size).append(')')
                        }
                        sb.append(']')
                    }

                    else -> simplyAppend(sb, message)
                }
            }
            sb.append(LOGGED_ITEMS_DELIMITER)
        } else {
            if (logNull) {
                sb.append(LOGGED_NULL)
                sb.append(LOGGED_ITEMS_DELIMITER)
            }
        }
    }

    private fun isArray(message: Any?): Boolean {
        return message is Array<*> ||
            message is ByteArray ||
            message is ShortArray ||
            message is IntArray ||
            message is LongArray ||
            message is FloatArray ||
            message is DoubleArray ||
            message is BooleanArray ||
            message is CharArray
    }

    private fun simplyAppend(
        sb: StringBuilder,
        message: Any?,
    ) {
        when (message) {
            is ByteArray -> simplyAppend(sb, message.toTypedArray())
            is ShortArray -> simplyAppend(sb, message.toTypedArray())
            is IntArray -> simplyAppend(sb, message.toTypedArray())
            is LongArray -> simplyAppend(sb, message.toTypedArray())
            is FloatArray -> simplyAppend(sb, message.toTypedArray())
            is DoubleArray -> simplyAppend(sb, message.toTypedArray())
            is BooleanArray -> simplyAppend(sb, message.toTypedArray())
            is CharArray -> simplyAppend(sb, message.toTypedArray())
            else -> sb.append(message)
        }
    }

    private fun simplyAppend(
        sb: StringBuilder,
        loggedArray: Array<*>,
    ) {
        sb.append('[')
        var i = 0
        while (i < loggedArray.size && i < MAX_ARRAY_LENGTH_LOGGED) {
            sb.append(loggedArray[i])
            sb.append(LOGGED_ITEMS_DELIMITER)
            i++
        }
        if (MAX_ARRAY_LENGTH_LOGGED < loggedArray.size) {
            sb.append("...(").append(loggedArray.size).append(')')
        }
        sb.append(']')
    }

    private fun checkIsPasswordString(
        logger: KLogger,
        sb: StringBuilder,
    ): Boolean {
        // filter only in "production mode"
        if (!filterPasswords || logger.isTraceEnabled()) {
            return false
        }
        val s = sb.toString().lowercase()
        for (pwStr in passwordBlackList) {
            if (s.contains(pwStr)) {
                return true
            }
        }
        return false
    }

    /**
     * Macro method to log that a method has been entered.
     *
     * @param logger The logger of the concrete implementation.
     * @param method The name of the method
     * @return The current system time (useful to measure durations)
     */
    fun logEnter(
        logger: KLogger,
        method: String,
    ): Long {
        return if (logger.isTraceEnabled()) {
            logger.trace { "entering $method" }
            currentTimeMillis()
        } else {
            0
        }
    }

    /**
     * Macro method to log that a method has been entered.
     *
     * @param logger The logger of the concrete implementation.
     * @param method The name of the method
     * @param args The arguments.
     * @return The current system time (useful to measure durations)
     */
    fun logEnter(
        logger: KLogger,
        method: String?,
        vararg args: Any?,
    ): Long {
        return if (logger.isTraceEnabled()) {
            logger.trace {
                val depth = 0
                val builder = StringBuilder()
                builder.append("entering ").append(method)
                for (i in args.indices) {
                    builder.append(if (i == 0) "(" else ", ")
                    simplyAppend(builder, args[i], true, depth)
                }
                builder.append(")")
                val s = builder.toString()
                sanitizeLogMessage(s)
            }
            currentTimeMillis()
        } else {
            0
        }
    }

    /**
     * Macro method to log that a method has been left without a result.
     *
     * @param logger The logger of the concrete implementation.
     * @param method The name of the method
     * @param start Start execution time of the method
     */
    fun logLeave(
        logger: KLogger,
        method: String,
        start: Long,
    ) {
        val duration: Long = currentTimeMillis() - start
        logger.trace { "leaving $method after $duration ms" }
    }

    /**
     * Macro method to log that a method has been left with a result.
     *
     * @param <T> the generic type
     * @param logger The logger of the concrete implementation.
     * @param method The name of the method
     * @param start Start execution time of the method
     * @param result The method result.
     * @return The result
     </T> */
    fun <T> logLeave(
        logger: KLogger,
        method: String,
        start: Long,
        result: T,
    ): T {
        val duration: Long = currentTimeMillis() - start
        logger.trace { "leaving $method after $duration ms" }
        return result
    }

    /**
     * Log at either DEBUG or INFO level, depending on the given flag value.
     *
     * @param logger the logger
     * @param isInfo the is info
     * @param message the message
     */
    fun debugOrInfo(
        logger: KLogger,
        isInfo: Boolean,
        message: String?,
    ) {
        if (isInfo) {
            logger.info { sanitizeLogMessage(message) }
        } else {
            logger.debug { sanitizeLogMessage(message) }
        }
    }

    /**
     * Sanitizes provided string in optimized way.
     * Characters for sanitizing: 0x09 (tab), 0x0a (newLine), 0x0b (whitespace), 0x0c (invalid), 0x0d (Carriage Return)
     *
     * @param s string to sanitize
     *
     * @return the sanitized string
     */
    private fun sanitizeLogMessage(s: String?): String? {
        if (s == null) {
            return null
        }

        var result: StringBuilder? = null
        var i = 0
        val length = s.length
        while (i < length) {
            val c = s[i]
            if (c.code <= 13) {
                if (result == null) {
                    result = StringBuilder(length + 8)
                    // increase the original size for 2 more entities (like for one CRLF sequence)
                    result.append(s.substring(0, i))
                }
                result.append("&#").append(c.code).append(';')
            } else {
                result?.append(c)
            }
            i++
        }
        return result?.toString() ?: s
    }

    /**
     * Returns the current time in milliseconds.
     *
     * @return the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC
     */
    private fun currentTimeMillis() = Clock.System.now().toEpochMilliseconds()
}
