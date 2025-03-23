package com.basket.core.common.designSystem.uikit.screen

sealed class UpdateState<out D, out S> {

    data class Data<D : DataModel>(val data: D) : UpdateState<D, Nothing>()

    data class State<D : DataModel, S : UIState<D, *>>(val state: S) : UpdateState<Nothing, S>()
}
