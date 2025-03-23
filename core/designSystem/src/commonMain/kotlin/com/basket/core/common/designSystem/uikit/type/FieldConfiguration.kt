package com.basket.core.common.designSystem.uikit.type

/**
 * @since 1.0.0
 */
interface FieldConfiguration

data class UndefinedFieldConfig(val maxContentLength: Int? = null) : FieldConfiguration

data class DoubleFieldConfig(
    val beforeDecimal: Int? = null,
    val afterDecimal: Int = 2,
    val maxValue: Double = Double.MAX_VALUE
) : FieldConfiguration

data class IntFieldConfig(
    val maxDigits: Int? = null,
    val maxValue: Int = Int.MAX_VALUE
) : FieldConfiguration

data class StringFieldConfig(val maxContentLength: Int? = null) : FieldConfiguration

data class TimeFieldConfig(
    val format: String = "HH:mm"
) : FieldConfiguration
