package com.basket.core.commmon.ktime

import com.basket.core.common.ktime.OffsetDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author vsouhrada (v.souhrada@gmail.com)
 */
class OffsetDateTimeTest {
    @Test
    fun testStringParser() {
        val offsetDateTime = OffsetDateTime.parse(DATETIME_ISO)
        assertEquals(DATETIME_ISO, offsetDateTime.toString())
    }

    @Test
    fun testJsonSerialization() {
        val offsetDateTime = OffsetDateTime.parse(DATETIME_ISO)
        val offsetDateTimeJson = Json.encodeToString(offsetDateTime)
        assertEquals(offsetDateTime.toString(), Json.decodeFromString(offsetDateTimeJson))
    }

    @Test
    fun testJsonSerializationByClockInstant() {
        val offsetDateTime = OffsetDateTime.ofInstant(Clock.System.now(), TimeZone.currentSystemDefault())
        val offsetDateTimeJson = Json.encodeToString(offsetDateTime)
        assertEquals(offsetDateTime.toString(), Json.decodeFromString<OffsetDateTime>(offsetDateTimeJson).toString())
    }

    private companion object {
        const val DATETIME_ISO = "2021-08-06T08:09:34.749787500+02:00"
    }
}
