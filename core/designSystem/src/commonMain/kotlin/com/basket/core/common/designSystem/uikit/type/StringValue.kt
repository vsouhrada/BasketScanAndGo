package com.basket.core.common.designSystem.uikit.type

data class StringValue<T>(
    val text: String,
    val value: T?,
    override val fieldConfiguration: StringFieldConfig = StringFieldConfig()
) : FieldType<StringFieldConfig> {

    fun getValueOrDefault(default: T? = null): T? {
        return value ?: default
    }

    override fun toString(): String {
        return value?.toString() ?: text
    }
}
