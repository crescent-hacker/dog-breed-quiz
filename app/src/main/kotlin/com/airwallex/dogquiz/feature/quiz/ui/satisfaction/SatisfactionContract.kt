package com.airwallex.dogquiz.feature.quiz.ui.satisfaction

import com.airwallex.core.mvi.MviContract
import com.airwallex.core.mvi.MviEffectMapper
import com.airwallex.core.mvi.MviEvent
import com.airwallex.core.mvi.MviEventProcessor
import com.airwallex.core.mvi.MviResult
import com.airwallex.core.mvi.MviStateReducer
import com.airwallex.core.mvi.MviResult.ProcessStatus
import com.airwallex.core.mvi.MviResult.ProcessStatus.*
import com.airwallex.core.mvi.MviViewEffect
import com.airwallex.core.mvi.MviViewState
import com.airwallex.core.util.TextHolder
import com.airwallex.core.util.toFlow
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionContract.*
import com.airwallex.feature.shared.error.genericErrorMessage
import com.airwallex.feature.shared.navigation.ScreenNavAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SatisfactionContract @Inject constructor(
) : MviContract<Event, ViewState, ViewEffect, Result>() {
    /***********************************************
     *               event processing              *
     ***********************************************/

    override val eventProcessor: MviEventProcessor<Event, Result> = MviEventProcessor { event ->
        when (event) {
            is Event.LoadDataEvent -> processLoadData(event.dto)
        }
    }

    private fun processLoadData(dto: SatisfactionNavDTO): Flow<Result> =
        merge(
            Result.LoadDataResult(IN_FLIGHT).toFlow(),
            Result.LoadDataResult(SUCCESS, dto = dto).toFlow().onStart { delay(500) }
        )

    /***********************************************
     *               state reducing                *
     ***********************************************/

    /**
     * Reduce to a new view state from the previous view state and the fetched result data
     */
    override val stateReducer: MviStateReducer<Result, ViewState> = MviStateReducer { prevState, result ->
        when (result) {
            is Result.LoadDataResult -> reduceLoadDataResult(prevState, result)
            is Result.NoResult -> prevState
        }
    }

    private fun reduceLoadDataResult(prevState: ViewState, result: Result.LoadDataResult): ViewState =
        when (result.processStatus) {
            IN_FLIGHT -> ViewState.Loading // used for full screen loading
            SUCCESS -> ViewState.Content(
                ViewData(
                    isSuccess = result.dto!!.isSuccess,
                    imageUrl = result.dto.imageUrl,
                    breedName = result.dto.breedName,
                )
            )
            FAILURE -> ViewState.Error(genericErrorMessage)
            else -> prevState
        }

    /***********************************************
     *               effect mapping                *
     ***********************************************/

    override val effectMapper: MviEffectMapper<Result, ViewEffect> = MviEffectMapper { result ->
        when (result) {
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
        data class LoadDataEvent(val dto: SatisfactionNavDTO) : Event()
    }

    /**
     * side effect
     */
    sealed class ViewEffect : MviViewEffect {
        data class NavigateEffect(val navAction: ScreenNavAction) : ViewEffect()
        data object NoEffect : ViewEffect()
    }

    /**
     * Result
     */
    sealed class Result(override val processStatus: ProcessStatus) : MviResult {
        data class LoadDataResult(
            override val processStatus: ProcessStatus,
            val dto: SatisfactionNavDTO? = null,
            val exception: Exception? = null
        ) : Result(processStatus)

        data object NoResult : Result(SUCCESS)
    }

    /**
     * View state
     */
    sealed class ViewState(override val status: MviViewState.Status) : MviViewState {
        data object Idle : ViewState(status = MviViewState.Status.IDLE)
        data object Loading : ViewState(status = MviViewState.Status.LOADING)
        data class Content(val data: ViewData) : ViewState(status = MviViewState.Status.CONTENT)
        data class Error(val errorMsg: TextHolder) : ViewState(status = MviViewState.Status.ERROR)
    }

    /**
     * View data
     */

    data class ViewData(
        val isSuccess: Boolean,
        val imageUrl: String,
        val breedName: String,
    ) {
        companion object {
            val EMPTY = ViewData(
                isSuccess = false,
                imageUrl = "",
                breedName = "",
            )
        }
    }
}
