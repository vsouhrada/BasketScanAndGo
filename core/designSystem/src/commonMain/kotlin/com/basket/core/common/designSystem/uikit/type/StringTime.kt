package com.basket.core.common.designSystem.uikit.type

// TODO under development
internal data class StringTime(
    val value: String,
    override val fieldConfiguration: TimeFieldConfig = TimeFieldConfig(),
) : FieldType<TimeFieldConfig> {
    /*fun getLocalTimeOrNull() = value.toDoubleOrNull()

    fun getLocalTime() = value.toDouble()

    fun getLocalTimeOrDefault(default: LocalTime = Clock.now()): Double {
       // return value.toDoubleOrNull() ?: default
    }*/

    override fun toString(): String {
        return value
    }
}

// fun LocalTime.toStringTime() = StringTime(this.toString())
