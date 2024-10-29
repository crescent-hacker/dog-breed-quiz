package com.airwallex.feature.shared.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airwallex.core.mvi.MviEvent
import com.airwallex.core.mvi.MviResult
import com.airwallex.core.mvi.MviViewEffect
import com.airwallex.core.mvi.MviViewModel
import com.airwallex.core.mvi.MviViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import com.airwallex.feature.shared.BuildConfig
import com.airwallex.feature.shared.util.launch
import com.airwallex.core.mvi.*
import com.airwallex.core.util.AppLogger
import com.airwallex.core.util.log

/**
 * Steps to create a view model:
 * 1. Create a Contract class that inherit [MviContract] and its generic type classes like [MviEvent], [MviResult], [MviViewState], etc
 * 2. Create a new view model and inherit [AbstractMviViewModel]
 * 3. put @HiltViewModel in front of the new view model
 * 4. put @Inject in front of constructor method (no matter have arg to inject or not)
 *
 * Example:
 *
 *      @HiltViewModel
 *      class ExampleViewModel @Inject constructor(
 *           private val exampleService: ExampleService
 *      ) : AbstractMviViewModel()
 */
abstract class AbstractMviViewModel<
        Event : MviEvent,
        ViewState : MviViewState,
        ViewEffect : MviViewEffect,
        EventResult : MviResult>
    : ViewModel(), MviViewModel<Event, ViewState, ViewEffect, EventResult> {

    /************************************
     *             Mvi Intent
     ************************************/

    private val eventEmitter = MutableSharedFlow<Event>()

    override fun dispatchEvent(event: Event) {
        launch {
            eventEmitter.emit(event)
        }
    }

    private val effectEmitter = MutableSharedFlow<ViewEffect>()

    override fun dispatchSideEffect(viewEffect: ViewEffect, delayMillis: Long) {
        launch {
            if (delayMillis > 0) {
                delay(delayMillis)
            }
            effectEmitter.emit(viewEffect)
        }
    }

    /************************************
     *             View State
     ************************************/

    abstract fun getInitViewState(): ViewState
    private val _initViewState: ViewState by lazy { getInitViewState() }

    private var _viewState: MutableState<ViewState> = mutableStateOf(_initViewState)
    override val viewState: State<ViewState> get() = _viewState

    private lateinit var viewStateJob: Job

    /************************************
     *             View Effect
     ************************************/

    private val _viewEffects: Channel<ViewEffect> = Channel()
    override val viewEffects: Flow<ViewEffect> = _viewEffects.receiveAsFlow()

    private lateinit var viewEffectJob: Job

    /************************************
     *             execute contract
     ************************************/

    @OptIn(FlowPreview::class)
    private fun subscribeToEvents() {
        if (BuildConfig.MVI_LOGGING) {
            AppLogger.debug("MviViewContract: start subscribe to events ")
        }

        val eventResults = eventEmitter
            .logMviViewContract("received event")
            .transform(getContract().eventFilter)
            .flatMapMerge { event ->
                getContract().eventProcessor.process(event)
                    .flowOn(Dispatchers.IO)
            }
            .logMviViewContract("event is processed to result")
            .shareIn(viewModelScope, WhileSubscribed())
            .logMviViewContract("event result is shared")

        viewStateJob = launch {
            // state reduce
            eventResults
                .scan(_initViewState, getContract().stateReducer::reduce)
                .logMviViewContract("reduced to state")
                .collectLatest {
                    _viewState.value = it
                }
        }

        viewEffectJob = launch {
            merge(
                eventResults
                    .filter { getContract().effectMapper != null }
                    .map {
                        getContract().effectMapper!!.mapToEffect(it)
                    },
                effectEmitter
            )
                .logMviViewContract("received effect")
                .collect {
                    _viewEffects.trySend(it)
                }
        }
    }

    private fun unsubscribeToEvents() {
        if (::viewStateJob.isInitialized) {
            viewStateJob.cancel()
            if (BuildConfig.MVI_LOGGING) {
                AppLogger.debug("MviViewContract: viewStateJob($viewStateJob) is cancelled")
            }
        }

        if (::viewEffectJob.isInitialized) {
            viewEffectJob.cancel()
            if (BuildConfig.MVI_LOGGING) {
                AppLogger.debug("MviViewContract: viewEffectJob($viewEffectJob) is cancelled")
            }
        }
    }

    /**
     * init function must be called for mvi viewModel
     */
    fun init() {
        val hasInitialized = ::viewStateJob.isInitialized && ::viewEffectJob.isInitialized
        if (!hasInitialized) {
            subscribeToEvents()
        }
    }

    public override fun onCleared() {

    }

    fun resetViewState() {
        unsubscribeToEvents()
        _viewState = mutableStateOf(_initViewState)
        subscribeToEvents()
    }

    private fun <T> Flow<T>.logMviViewContract(action: String) =
        if (BuildConfig.MVI_LOGGING) {
            log("MviViewContract: $action")
        } else this
}
