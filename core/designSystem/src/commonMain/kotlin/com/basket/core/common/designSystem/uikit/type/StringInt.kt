package com.basket.core.common.designSystem.uikit.type

data class StringInt(
    val value: String,
    override val fieldConfiguration: IntFieldConfig = IntFieldConfig(),
) : FieldType<IntFieldConfig> {
    fun getIntOrNull() = value.toIntOrNull()

    fun getInt() = value.toInt()

    fun getIntOrDefault(default: Int = 0): Int {
        return value.toIntOrNull() ?: default
    }

    override fun toString(): String {
        return value
    }
}

fun Int.toStringInt() = StringInt(this.toString())
