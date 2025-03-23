package com.basket.core.common.designSystem.uikit.screen

abstract class UIState<T : DataModel, E> {
    open val isLoading: Boolean = false
    open val data: T? = null
    open val error: E? = null
}

abstract class UIStateList<T : DataModel, E> {
    open val isLoading: Boolean = false
    open val data: List<T>? = null
    open val error: E? = null
}

open class DataModel

/*abstract class ErrorModel {

    abstract val title: String
    abstract val message: String
}*/
