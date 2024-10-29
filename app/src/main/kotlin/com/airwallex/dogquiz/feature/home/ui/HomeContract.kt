package com.airwallex.dogquiz.feature.home.ui

import com.airwallex.core.mvi.MviContract
import com.airwallex.core.mvi.MviEffectMapper
import com.airwallex.core.mvi.MviEvent
import com.airwallex.core.mvi.MviEventProcessor
import com.airwallex.core.mvi.MviResult
import com.airwallex.core.mvi.MviResult.ProcessStatus
import com.airwallex.core.mvi.MviResult.ProcessStatus.FAILURE
import com.airwallex.core.mvi.MviResult.ProcessStatus.IN_FLIGHT
import com.airwallex.core.mvi.MviResult.ProcessStatus.SUCCESS
import com.airwallex.core.mvi.MviStateReducer
import com.airwallex.core.mvi.MviViewEffect
import com.airwallex.core.mvi.MviViewState
import com.airwallex.core.util.TextHolder
import com.airwallex.core.util.mergeWith
import com.airwallex.core.util.toFlow
import com.airwallex.dogquiz.feature.home.ui.HomeContract.Event
import com.airwallex.dogquiz.feature.home.ui.HomeContract.Result
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewData.Companion.EMPTY
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewEffect
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewState
import com.airwallex.dogquiz.feature.quiz.data.repository.DogBreedRepository
import com.airwallex.dogquiz.navigation.AppNavAction
import com.airwallex.feature.shared.error.genericErrorMessage
import com.airwallex.feature.shared.navigation.ScreenNavAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeContract @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : MviContract<Event, ViewState, ViewEffect, Result>() {
    /***********************************************
     *               event processing              *
     ***********************************************/
    override val eventProcessor: MviEventProcessor<Event, Result> = MviEventProcessor { event ->
        when (event) {
            is Event.LoadDataEvent -> processLoadData(event)
            is Event.StartQuizEvent -> processStartQuiz()
        }
    }

    private fun processLoadData(event: Event.LoadDataEvent): Flow<Result> =
            dogBreedRepository.getDogBreeds(refresh = event.refresh)
                .map {
                    if (it.isSuccess) {
                        Result.LoadDataResult(SUCCESS)
                    } else {
                        Result.LoadDataResult(FAILURE, it.exceptionOrNull()!!)
                    }
                }
                .mergeWith(Result.LoadDataResult(IN_FLIGHT).toFlow()) // loading state

    private fun processStartQuiz(): Flow<Result> =
        Result.StartQuizResult.toFlow()

    /***********************************************
     *               state reducing                *
     ***********************************************/

    /**
     * Reduce to a new view state from the previous view state and the fetched result data
     */
    override val stateReducer: MviStateReducer<Result, ViewState> =
        MviStateReducer { prevState, result ->
            when (result) {
                is Result.LoadDataResult -> reduceLoadDataResult(prevState, result)
                else -> prevState
            }
        }

    private fun reduceLoadDataResult(
        prevState: ViewState,
        result: Result.LoadDataResult
    ): ViewState =
        when (result.processStatus) {
            IN_FLIGHT -> ViewState.Loading // used for full screen loading
            SUCCESS -> ViewState.Content(
                data = EMPTY
            )

            FAILURE -> ViewState.Error(genericErrorMessage)
            else -> prevState
        }

    /***********************************************
     *               effect mapping                *
     ***********************************************/

    override val effectMapper: MviEffectMapper<Result, ViewEffect> = MviEffectMapper { result ->
        when (result) {
            is Result.StartQuizResult -> ViewEffect.NavigateEffect(
                navAction = AppNavAction.NavigateToQuiz
            )

            else -> ViewEffect.NoEffect
        }
    }

    /***********************************************
     *           contract components               *
     ***********************************************/

    /**
     * event
     */
    sealed class Event : MviEvent {
        data class LoadDataEvent(val refresh: Boolean = false) : Event()
        data object StartQuizEvent : Event()
    }

    /**
     * side effect
     */
    sealed class ViewEffect : MviViewEffect {
        data class NavigateEffect(
            val navAction: ScreenNavAction
        ) : ViewEffect()

        data object NoEffect : ViewEffect()
    }

    /**
     * Result
     */
    sealed class Result(override val processStatus: ProcessStatus) : MviResult {
        data class LoadDataResult(
            override val processStatus: ProcessStatus,
            val exception: Throwable? = null
        ) : Result(processStatus)

        data object StartQuizResult : Result(SUCCESS)
    }

    /**
     * View state
     */
    sealed class ViewState(override val status: MviViewState.Status) : MviViewState {
        data object Idle : ViewState(status = MviViewState.Status.IDLE)
        data object Loading :
            ViewState(status = MviViewState.Status.LOADING)

        data class Content(val data: ViewData) : ViewState(status = MviViewState.Status.CONTENT)
        data class Error(val errorMsg: TextHolder) : ViewState(status = MviViewState.Status.ERROR)
    }

    /**
     * View data
     */

    data class ViewData(
        val isRefreshing: Boolean, // optional, used for screen that need shimmer effect
    ) {
        companion object {
            val EMPTY = ViewData(
                isRefreshing = false,
            )
        }
    }
}
