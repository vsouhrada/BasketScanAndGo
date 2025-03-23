package com.basket.sample.scango.presentation.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenState
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT, ACTION>(initialState: STATE) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialState
    )

    protected val _actionState = MutableSharedFlow<ACTION>(replay = 0)
    val actionState = _actionState.asSharedFlow()

    @Throws(CancellationException::class)
    abstract fun sendScreenEvent(event: EVENT)

    abstract fun clearErrorState()

    protected suspend fun emitScreenAction(action: ACTION) {
        _actionState.emit(action)
    }

    /**
     * Updates the state of the ViewModel.
     *
     * @param update A function that takes the current state and returns a new state.
     */
    protected fun updateState(update: (STATE) -> STATE) {
        _state.update(update)
    }

    /**
     * Launches a coroutine in the ViewModel's scope.
     *
     * @param block The coroutine block to execute.
     */
    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
}
