package com.android.developer.sequis_test.core.presentation.base

import androidx.lifecycle.ViewModel
import com.android.developer.sequis_test.core.presentation.plugins.UIEvent
import com.android.developer.sequis_test.core.presentation.plugins.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet

/**
 * Template for ViewModel
 *
 * @see ViewModel
 */
abstract class BaseViewModel<STATE : UIState, EVENT : UIEvent>(initialState: STATE) : ViewModel() {

    /**
     * Mutable State of this ViewModel
     */
    private val _state = MutableStateFlow(initialState)

    /**
     * State to be exposed to the UI layer
     */
    val state: StateFlow<STATE> = _state.asStateFlow()

    /**
     * Retrieves the current UI state
     */
    val currentState: STATE get() = state.value


    /**
     *  Method to set event from screen
     */
    fun setEvent(value: EVENT) = value.onEvent()


    /**
     * Method to run function from child
     */
    protected abstract fun EVENT.onEvent()

    /**
     * Method to observe error field
     */

    /**
     * Updates the state of this ViewModel and returns the new state
     *
     * @param update Lambda callback with old state to calculate a new state
     * @return The updated state
     */
    protected fun setState(update: (old: STATE) -> STATE): STATE = _state.updateAndGet(update)
}