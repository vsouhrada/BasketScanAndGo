package com.basket.core.common.ktime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateUtils {
    fun getOffsetDateTime(entryDate: LocalDate, entryTime: LocalTime,) = OffsetDateTime.ofInstant(
        instant =
        LocalDateTime(
            entryDate,
            entryTime,
        ).toInstant(timeZone = TimeZone.currentSystemDefault()),
    )

    fun currentDate() = OffsetDateTime.now(offset = TimeZone.currentSystemDefault()).dateTime.date

    fun currentTime() = OffsetDateTime.now(offset = TimeZone.currentSystemDefault()).dateTime.time

    fun formatDate(date: LocalDate, withYear: Boolean,): String {
        return date.formatFullDayWithYear(delimiter = ". ", useFullYear = withYear)
    }

    fun formatFromToDate(dateFrom: LocalDate, dateTo: LocalDate,): String {
        return "${formatDate(dateFrom, dateFrom.year != dateTo.year)} - ${formatDate(dateTo, true)}"
    }

    fun OffsetDateTime.withLocalTimeZone() = atZoneSameInstant(TimeZone.currentSystemDefault())

    fun LocalDateTime.asUTC() = toInstant(TimeZone.currentSystemDefault()).toLocalDateTime(TimeZone.UTC)

    fun formatSecondsToHHmm(seconds: Int): String {
        val hours = seconds / 3600
        val remainingSeconds = seconds % 3600
        val minutes = remainingSeconds / 60

        val hh = hours.toString().padStart(2, '0')
        val mm = minutes.toString().padStart(2, '0')

        return "$hh:$mm"
    }

    fun formatSecondsDecimalToHHmmss(seconds: Double): String {
        val hours = (seconds / 3600).toInt()
        val remainingSeconds = seconds % 3600
        val minutes = (remainingSeconds / 60).toInt()
        val remainingSecondsAfterMinutes = (remainingSeconds % 60).toInt()

        val hh = hours.toString().padStart(2, '0')
        val mm = minutes.toString().padStart(2, '0')
        val ss = remainingSecondsAfterMinutes.toString().padStart(2, '0')

        return "$hh:$mm:$ss"
    }

    @Throws(IllegalArgumentException::class)
    fun convertHHmmssToSeconds(timeString: String): Double {
        val parts = timeString.split(":")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid time format. Expected HH:mm:ss")
        }

        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        val seconds = parts[2].toInt()

        return hours * 3600.0 + minutes * 60.0 + seconds.toDouble()
    }

    @Throws(IllegalArgumentException::class)
    fun convertHHmmToSeconds(timeString: String): Double {
        val parts = timeString.split(":")
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid time format. Expected HH:mm")
        }

        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        return hours * 3600.0 + minutes * 60.0
    }
}
