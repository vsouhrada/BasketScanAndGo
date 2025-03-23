package com.basket.core.common.designSystem.uikit.type

/**
 * @since 1.1.0
 */
data class StringType(
    val value: String,
    override val fieldConfiguration: StringFieldConfig = StringFieldConfig(),
) : FieldType<StringFieldConfig> {
    override fun toString(): String {
        return value
    }
}
