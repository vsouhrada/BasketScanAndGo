@file:JvmName("TimeUtils")

package com.basket.core.commmon.ktime

import com.basket.core.common.ktime.OffsetDateTime
import com.basket.core.common.ktime.formatYYYYeMMeDDTHHiMMiSS
import com.basket.core.common.ktime.formatYYYYeMMeDDTHHiMMiSSoSSS
import com.basket.core.common.ktime.formatYYYYeMMeDDTHHiMMiSSoSSSZ
import com.basket.core.common.ktime.formatddMMYY
import com.basket.core.common.ktime.parseFromEpochMillis
import com.basket.core.common.ktime.parseYYYYeMMeDDTHHiMMiSSoSSS
import com.basket.core.common.ktime.parseYYYYeMMeDDTHHiMMiSSoSSSZ
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmName
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeKtxTest {
    @Test
    fun test_formatddMMYY() {
        val formattedDateTime = OffsetDateTime.ofInstant(Instant.fromEpochMilliseconds(0)).dateTime.formatddMMYY()
        assertEquals("010170", formattedDateTime)
    }

    @Test
    fun test_formatYYYYeMMeDDTHHiMMiSS() {
        assertEquals(
            expected = "1970-01-01T00:00:42",
            actual =
            OffsetDateTime.ofInstant(
                Instant.fromEpochMilliseconds(42000),
            ).dateTime.formatYYYYeMMeDDTHHiMMiSS(),
        )

        assertEquals(
            expected = "2022-04-04T10:59:16",
            actual = "2022-04-04T10:59:16.999999".toLocalDateTime().formatYYYYeMMeDDTHHiMMiSS(),
        )
    }

    @Test
    fun test_formatYYYYeMMeDDTHHiMMiSSoSSS() {
        assertEquals(
            expected = "1970-01-01T00:00:00.042",
            actual =
            OffsetDateTime.ofInstant(
                Instant.fromEpochMilliseconds(42),
            ).dateTime.formatYYYYeMMeDDTHHiMMiSSoSSS(),
        )

        assertEquals(
            expected = "2022-04-04T10:59:16.999",
            actual = "2022-04-04T10:59:16.999999".toLocalDateTime().formatYYYYeMMeDDTHHiMMiSSoSSS(),
        )
    }

    @Test
    fun test_formatYYYYeMMeDDTHHiMMiSSoSSSZ() {
        assertEquals(
            expected = "1970-01-01T00:00:00.042Z",
            actual =
            OffsetDateTime.ofInstant(
                Instant.fromEpochMilliseconds(42),
            ).dateTime.formatYYYYeMMeDDTHHiMMiSSoSSSZ(),
        )

        assertEquals(
            expected = "2022-04-04T10:59:16.999Z",
            actual = "2022-04-04T10:59:16.999999".toLocalDateTime().formatYYYYeMMeDDTHHiMMiSSoSSSZ(),
        )
    }

    @Test
    fun test_parseYYYYeMMeDDTHHiMMiSSoSSS() {
        val timeZone = TimeZone.of("Europe/Prague")

        // summer/DS time
        val dateTimeSummer = "2022-06-06T00:00:00.042"
        val offsetDateTimeSummer = parseYYYYeMMeDDTHHiMMiSSoSSS(dateTimeSummer, timeZone)
        assertEquals(dateTimeSummer, offsetDateTimeSummer.dateTime.toString())
        assertEquals("+02:00", offsetDateTimeSummer.offset.toString())

        // regular time
        val dateTime = "2022-10-31T00:00:00.042"
        val offsetDateTime = parseYYYYeMMeDDTHHiMMiSSoSSS(dateTime, timeZone)
        assertEquals(dateTime, offsetDateTime.dateTime.toString())
        assertEquals("+01:00", offsetDateTime.offset.toString())
    }

    @Test
    fun test_parseYYYYeMMeDDTHHiMMiSSoSSSZ() {
        val dateTime = "1970-01-01T00:00:01.001Z"

        val offsetDateTime = parseYYYYeMMeDDTHHiMMiSSoSSSZ(dateTime)

        assertEquals(dateTime, offsetDateTime.toString())
        assertEquals("Z", offsetDateTime.offset.toString())
    }

    @Test
    fun test_parseFromEpochMillis() {
        val timeZone = TimeZone.of("Europe/Prague")

        // summer/DS time
        val dateTimeSummer = "2022-06-06T00:00:00.042Z"
        val millisSummer = OffsetDateTime.parse(string = dateTimeSummer).toInstant().toEpochMilliseconds()
        val parsedOffsetDateTimeSummer = parseFromEpochMillis(epochMillis = millisSummer, timeZone = timeZone)
        val parsedMillisSummer = parseFromEpochMillis(
            epochMillis = millisSummer,
            timeZone = timeZone
        ).toInstant().toEpochMilliseconds()

        assertEquals(millisSummer, parsedMillisSummer)
        assertEquals("+02:00", parsedOffsetDateTimeSummer.offset.toString())

        // regular time
        val dateTime = "2022-10-31T00:00:00.042Z"
        val millis = OffsetDateTime.parse(string = dateTime).toInstant().toEpochMilliseconds()
        val parsedOffsetDateTime = parseFromEpochMillis(epochMillis = millis, timeZone = timeZone)
        val parsedMillis = parseFromEpochMillis(
            epochMillis = millis,
            timeZone = timeZone
        ).toInstant().toEpochMilliseconds()

        assertEquals(millis, parsedMillis)
        assertEquals("+01:00", parsedOffsetDateTime.offset.toString())
    }
}
