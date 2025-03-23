package com.basket.core.common.ktime

import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.asTimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents a [LocalDateTime] and the respective [FixedOffsetTimeZone] of it.
 *
 * @author vsouhrada (v.souhrada@gmail.com)
 * @since 0.1.0
 */
@Serializable(with = OffsetDateTimeSerializers::class)
public class OffsetDateTime private constructor(val dateTime: LocalDateTime, val offset: FixedOffsetTimeZone) {
    override fun toString(): String {
        return if (offset.offset.totalSeconds == 0) {
            "${dateTime}Z"
        } else {
            "$dateTime$offset"
        }
    }

    /**
     * Converts the [OffsetDateTime] to an [Instant]. This looses the [FixedOffsetTimeZone] information, because the date and time
     * is converted to UTC in the process.
     */
    public fun toInstant(): Instant = dateTime.toInstant(offset)

    /**
     * Returns a new [OffsetDateTime] with the given [TimeZone].
     */
    public fun atZoneSameInstant(newTimeZone: TimeZone): OffsetDateTime {
        val instant = dateTime.toInstant(offset)
        val newDateTime = instant.toLocalDateTime(newTimeZone)
        return OffsetDateTime(newDateTime, newTimeZone.offsetAt(instant).asTimeZone())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OffsetDateTime

        if (dateTime != other.dateTime) return false
        if (offset != other.offset) return false
        return true
    }

    override fun hashCode() = dateTime.hashCode()

    companion object {
        private val zoneColonRegex by lazy {
            Regex(""".*[+\-][0-9]{2}:[0-9]{2}$""")
        }

        private val zoneRegex by lazy {
            Regex(""".*[+\-][0-9]{2}[0-9]{2}$""")
        }

        /**
         * Parses an [OffsetDateTime] from a RFC3339 compatible string.
         */
        @JvmStatic
        public fun parse(string: String): OffsetDateTime = when {
            string.contains('Z') ->
                OffsetDateTime(
                    LocalDateTime.parse(string.substringBefore('Z')),
                    FixedOffsetTimeZone(TimeZone.UTC.offsetAt(Instant.fromEpochMilliseconds(0))),
                )
            string.contains('z') -> {
                OffsetDateTime(
                    LocalDateTime.parse(string.substringBefore('z')),
                    TimeZone.UTC.offsetAt(Instant.fromEpochMilliseconds(0)).asTimeZone(),
                )
            }
            zoneColonRegex.containsMatchIn(string) -> {
                val dateTime = LocalDateTime.parse(string.substring(0, string.length - 6))
                val tz = TimeZone.of(string.substring(string.length - 6))
                val instant = dateTime.toInstant(tz)
                val offset = tz.offsetAt(instant)
                OffsetDateTime(
                    dateTime,
                    offset.asTimeZone(),
                )
            }
            zoneRegex.containsMatchIn(string) -> {
                val dateTime = LocalDateTime.parse(string.substring(0, string.length - 5))
                val tz = TimeZone.of(string.substring(string.length - 5))
                val instant = dateTime.toInstant(tz)
                val offset = tz.offsetAt(instant)
                OffsetDateTime(
                    dateTime,
                    FixedOffsetTimeZone(offset),
                )
            }
            string.contains('T') -> {
                OffsetDateTime(
                    LocalDateTime.parse(string),
                    TimeZone.UTC.offsetAt(Instant.fromEpochMilliseconds(0)).asTimeZone(),
                )
                // throw IllegalArgumentException("Date \"$string\" ISO 8601 compatible - Missing TimeZone info!!!")
            }
            else -> throw IllegalArgumentException("Date \"$string\" is not RFC3339 or  ISO 8601 compatible")
        }

        /**
         * Creates an [OffsetDateTime] from an [Instant] in a given [TimeZone] ([TimeZone.UTC] by default).
         */
        @JvmStatic
        fun ofInstant(instant: Instant, offset: TimeZone = TimeZone.UTC,): OffsetDateTime = OffsetDateTime(
            instant.toLocalDateTime(offset),
            offset.offsetAt(instant).asTimeZone(),
        )

        /**
         *
         */
        @JvmStatic
        fun of(dateTime: LocalDateTime, offset: FixedOffsetTimeZone,): OffsetDateTime = OffsetDateTime(dateTime, offset)

        @JvmStatic
        @JvmOverloads
        fun now(offset: TimeZone = TimeZone.UTC) = ofInstant(instant = Clock.System.now(), offset = offset)
    }
}
