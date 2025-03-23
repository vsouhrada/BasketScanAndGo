package com.basket.core.common.designSystem.uikit.widget

import com.basket.core.common.designSystem.uikit.type.StringType

sealed class Visibility {

    object Visible : Visibility()

    object Invisible : Visibility()

    object Gone : Visibility()
}

/**
 * @since 1.1.0
 */
interface BaseField<T : Any?> {
    val value: T
    val visibility: Visibility
    val isEditable: Boolean
    val color: FieldColor
    var errorState: ErrorFieldState?
    val isModified: Boolean
    val placeholder: String?
    val isValueProtected: Boolean
//    val fieldTypeConfiguration: FieldTypeConfiguration
}

data class Field<T : Any?>(
    override val value: T,
    val label: String? = null, // TODO refactor string label with LabelString
    val suffix: String? = null,
    override val visibility: Visibility = Visibility.Visible,
    override val isEditable: Boolean = true,
    override val color: FieldColor = FieldColor.None,
    override var errorState: ErrorFieldState? = null,
    override val isModified: Boolean = false,
    override val placeholder: String? = null,
    override val isValueProtected: Boolean = false,
) : BaseField<T> {

    /* override val fieldTypeConfiguration: FieldTypeConfiguration
         get() = when (value is FieldType) {
             true -> value.fieldTypeConfiguration
             else -> FieldTypeConfiguration.UndefinedTypeConfig
         }*/
}

data class LabelField<T : Any>(
    val label: String,
    val suffix: String? = null,
    val valueColor: FieldColor = FieldColor.None,
    override val color: FieldColor = FieldColor.None,
    override val value: T,
    override val visibility: Visibility = Visibility.Visible,
    override val isEditable: Boolean = false,
    override var errorState: ErrorFieldState? = null,
    override val isModified: Boolean = false,
    override val placeholder: String? = null,
    override val isValueProtected: Boolean = false,
) : BaseField<T> {

    /*override val fieldTypeConfiguration: FieldTypeConfiguration
        get() = when (value is FieldType) {
            true -> value.fieldTypeConfiguration
            else -> FieldTypeConfiguration.UndefinedTypeConfig
        }*/
}

data class StringField(
    val maxContentLength: Int? = null,
    val label: String? = null,
    val suffix: String? = null,
    override val value: StringType,
    override val visibility: Visibility,
    override val isEditable: Boolean,
    override val color: FieldColor,
    override var errorState: ErrorFieldState?,
    override val isModified: Boolean,
    override val placeholder: String? = null,
    override val isValueProtected: Boolean = false,
) : BaseField<StringType> {

    /* override val fieldTypeConfiguration: FieldTypeConfiguration = FieldTypeConfiguration.StringTypeConfig(
         contentLength = maxContentLength
     )*/
}

data class ErrorFieldState(val message: String)

sealed class FieldColor {

    object None : FieldColor()

    object Red : FieldColor()

    object Green : FieldColor()

    object Blue : FieldColor()

    data class Hex(val hexCode: String) : FieldColor()
}

inline fun <T : Any> T?.visibleIfPredicate(predicate: (T?) -> Boolean) =
    if (this != null && predicate(this)) {
        Visibility.Visible
    } else {
        Visibility.Gone
    }

fun <T : Any> T?.visibleIfNotNull() = if (this != null) {
    Visibility.Visible
} else {
    Visibility.Gone
}

fun <T : Any> Field<T>.isVisible() = visibility == Visibility.Visible
