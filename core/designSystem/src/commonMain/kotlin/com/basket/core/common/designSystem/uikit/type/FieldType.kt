package com.basket.core.common.designSystem.uikit.type

/**
 * @since 1.1.0
 */
interface FieldType<T : FieldConfiguration> {
    val fieldConfiguration: T

    override fun toString(): String
}
