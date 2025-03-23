@file:JvmName("TimeUtils")

/**
 * Disclaimer & TODO
 * method `formatYYYYeMMeDDTHHiMMiSSoSSS` doesn't to the half-up rounding of milliseconds anymore
 * the rounding was removed otherwise, we would need to handle transition to higher time units
 * eg. 10:59:59.999999 --- to millis ----> 11:00:00:000
 * but we are not able to do it reliably due to the missing zone information (daylight savings etc)
 * -> discussion how we address that?
 */

package com.basket.core.common.ktime

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.asTimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.offsetAt
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmName

fun LocalDateTime.formatddMMYY(timeZone: TimeZone? = null): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this
    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val year = dateTime.year.toString().takeLast(2)
    return "$day$month$year"
}

fun LocalDateTime.formatFullDayWithYear(
    useLeadingZeros: Boolean = false,
    startWithYear: Boolean = false,
    delimiter: String = "-",
    useFullYear: Boolean = false,
    timeZone: TimeZone? = null,
): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this
    val day =
        dateTime.dayOfMonth.toString().also {
            if (useLeadingZeros) {
                it.padStart(2, '0')
            }
        }
    val month =
        dateTime.monthNumber.toString().also {
            if (useLeadingZeros) {
                it.padStart(2, '0')
            }
        }
    val year =
        dateTime.year.toString().also {
            if (!useFullYear) {
                it.takeLast(2)
            }
        }

    return if (startWithYear) {
        "$year$delimiter$month$delimiter$day"
    } else {
        "$day$delimiter$month$delimiter$year"
    }
}

fun LocalDate.formatFullDayWithYear(
    useLeadingZeros: Boolean = false,
    startWithYear: Boolean = false,
    delimiter: String = ".",
    useFullYear: Boolean = false,
): String {
    val day =
        this.dayOfMonth.toString().also {
            if (useLeadingZeros) {
                it.padStart(2, '0')
            }
        }
    val month =
        this.monthNumber.toString().also {
            if (useLeadingZeros) {
                it.padStart(2, '0')
            }
        }
    val year =
        this.year.toString().also {
            if (!useFullYear) {
                it.takeLast(2)
            }
        }

    return if (startWithYear) {
        "$year$delimiter$month$delimiter$day"
    } else {
        "$day$delimiter$month$delimiter$year"
    }
}

fun LocalDateTime.formatYYYYeMMeDDTHHiMMiSS(timeZone: TimeZone? = null): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this
    return "${dateTime.year}-" +
        "${dateTime.monthNumber.toString().padStart(2, '0')}-" +
        "${dateTime.dayOfMonth.toString().padStart(2, '0')}T" +
        "${dateTime.hour.toString().padStart(2, '0')}:" +
        "${dateTime.minute.toString().padStart(2, '0')}:" +
        dateTime.second.toString().padStart(2, '0')
}

fun LocalDateTime.formatHHiMMiSS(timeZone: TimeZone? = null): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this

    return "${dateTime.hour.toString().padStart(2, '0')}:" +
        "${dateTime.minute.toString().padStart(2, '0')}:" +
        dateTime.second.toString().padStart(2, '0')
}

fun LocalTime.formatHHiMMiSS(): String {
    return "${this.hour.toString().padStart(2, '0')}:" +
        "${this.minute.toString().padStart(2, '0')}:" +
        this.second.toString().padStart(2, '0')
}

fun LocalDateTime.formatYYYYeMMeDDTHHiMMiSSoSSS(timeZone: TimeZone? = null): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this
    return "${dateTime.formatYYYYeMMeDDTHHiMMiSS()}." +
        (dateTime.nanosecond / 1000000.0).toInt().toString().padStart(3, '0')
}

fun LocalDateTime.formatYYYYeMMeDDTHHiMMiSSoSSSZ(timeZone: TimeZone? = null): String {
    val dateTime =
        timeZone?.let {
            this.toInstant(UtcOffset.ZERO).toLocalDateTime(it)
        } ?: this

    return "${dateTime.formatYYYYeMMeDDTHHiMMiSSoSSS()}Z"
}

fun LocalDate.atStartOfMonth() = LocalDate(year, month, 1)

fun LocalDate.atEndOfMonth(): LocalDate {
    val atNextMonth = plus(DatePeriod(months = 1))
    return LocalDate(atNextMonth.year, atNextMonth.month, 1)
        .minus(DatePeriod(days = 1))
}

fun LocalDateTime.isSameMonthInYear(other: LocalDateTime): Boolean {
    return date.year == other.year && month == other.month
}

fun LocalDateTime.isSameDayInYear(other: LocalDateTime): Boolean {
    return date.year == other.year && month == other.month && date.dayOfMonth == other.dayOfMonth
}

fun LocalDate.isSameDayInYear(other: LocalDate): Boolean {
    return year == other.year && month == other.month && dayOfMonth == other.dayOfMonth
}

fun LocalDate.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val now = Clock.System.now().toLocalDateTime(timeZone)

    return atTime(now.time).toInstant(timeZone).toEpochMilliseconds()
}

fun parseYYYYeMMeDDTHHiMMiSSoSSS(string: String, timeZone: TimeZone,): OffsetDateTime {
    val localDateTime = string.toLocalDateTime()
    return OffsetDateTime.of(
        dateTime = string.toLocalDateTime(),
        offset = getOffset(instant = localDateTime.toInstant(timeZone), timeZone = timeZone),
    )
}

fun parseYYYYeMMeDDTHHiMMiSSoSSSZ(string: String): OffsetDateTime {
    return OffsetDateTime.parse(string = string)
}

fun parseFromEpochMillis(epochMillis: Long, timeZone: TimeZone,): OffsetDateTime {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    return OffsetDateTime.ofInstant(
        instant = instant,
        offset = getOffset(instant = instant, timeZone = timeZone),
    )
}

fun convertMillisToLocalDate(millis: Long) = Instant.fromEpochMilliseconds(millis).toLocalDateTime(
    TimeZone.currentSystemDefault(),
).date

private fun getOffset(instant: Instant, timeZone: TimeZone,): FixedOffsetTimeZone {
    return timeZone.offsetAt(instant).asTimeZone()
}
