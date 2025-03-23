@file:JvmName("KotlinLoggingUtils")

package com.basket.core.common.klogger

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.jvm.JvmName

fun getKLogger(func: () -> Unit): KLogger {
    return KotlinLogging.logger(func)
}

fun getKLogger(name: String) = KotlinLogging.logger(name)
