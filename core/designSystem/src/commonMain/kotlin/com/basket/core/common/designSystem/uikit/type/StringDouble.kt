package com.basket.core.common.designSystem.uikit.type

data class StringDouble(
    val value: String,
    override val fieldConfiguration: DoubleFieldConfig =
        DoubleFieldConfig(afterDecimal = 2, beforeDecimal = null)
) : FieldType<DoubleFieldConfig> {

    fun getDoubleOrNull() = value.toDoubleOrNull()

    fun getDouble() = value.toDouble()

    fun getDoubleOrDefault(default: Double = 0.0): Double {
        return value.toDoubleOrNull() ?: default
    }

    override fun toString(): String {
        return value
    }
}

fun Double.toStringDouble() = StringDouble(this.toString())
