package com.airwallex.dogquiz.feature.quiz.ui.multiplechoice

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
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedDTO
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.data.repository.DogBreedRepository
import com.airwallex.dogquiz.feature.quiz.navigation.QuizNavAction
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.Event
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.Result
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewEffect
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewState
import com.airwallex.feature.shared.error.genericErrorMessage
import com.airwallex.feature.shared.navigation.ScreenNavAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MultipleChoiceContract @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : MviContract<Event, ViewState, ViewEffect, Result>() {
    /***********************************************
     *               event processing              *
     ***********************************************/

    override val eventProcessor: MviEventProcessor<Event, Result> = MviEventProcessor { event ->
        when (event) {
            is Event.LoadDataEvent -> processLoadData()
            is Event.CheckAnswerEvent -> processCheckAnswer(event)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processLoadData(): Flow<Result> =
        dogBreedRepository.getDogBreeds()
            // load dog breed list and pick a random breed to be shown
            .map {
                if (it.isFailure)
                    return@map Result.LoadDataResult(processStatus = FAILURE, exception = it.exceptionOrNull()!!)

                val options = it.getOrThrow().breeds.shuffled().take(4)
                val selectedOption = options.random()

                Result.LoadDataResult(
                    processStatus = SUCCESS,
                    breedOptions = options,
                    breedToBeShown = selectedOption
                )
            }
            // fetch random image for the picked breed
            .flatMapLatest { result ->
                // fetch random image for the picked breed
                dogBreedRepository.getDogBreedRandomImage(result.breedToBeShown)
                    .map {
                        if (it.isFailure)
                            return@map Result.LoadDataResult(processStatus = FAILURE, exception = it.exceptionOrNull()!!)

                        result.copy(
                            breedToBeShown = result.breedToBeShown.copy(
                                imageUrl = it.getOrThrow()
                            )
                        )
                    }
            }
            // loading signal when fetching data
            .mergeWith(
                Result.LoadDataResult(IN_FLIGHT).toFlow()
            )

    private fun processCheckAnswer(event: Event.CheckAnswerEvent): Flow<Result> =
        Result.CheckAnswerResult(
            isCorrect = event.breedSelected.displayName == event.breedCorrectAnswer.displayName,
            breedCorrectAnswer = event.breedCorrectAnswer
        ).toFlow()


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
                is Result.CheckAnswerResult -> prevState
                is Result.NoResult -> prevState
            }
        }

    private fun reduceLoadDataResult(
        prevState: ViewState,
        result: Result.LoadDataResult
    ): ViewState =
        when (result.processStatus) {
            IN_FLIGHT -> ViewState.Loading // used for full screen loading
            SUCCESS -> ViewState.Content(
                data = ViewData(
                    breedOptions = result.breedOptions,
                    breedCorrectAnswer = result.breedToBeShown
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
            is Result.CheckAnswerResult ->
                ViewEffect.NavigateEffect(
                    navAction = QuizNavAction.NavigateToSatisfaction(
                        dto = SatisfactionNavDTO(
                            isSuccess = result.isCorrect,
                            breedName = result.breedCorrectAnswer.displayName,
                            imageUrl = result.breedCorrectAnswer.imageUrl!!
                        )
                    )
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
        data object LoadDataEvent : Event()
        data class CheckAnswerEvent(val breedSelected: DogBreedDTO, val breedCorrectAnswer: DogBreedDTO) : Event()
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
            val breedOptions: List<DogBreedDTO> = emptyList(),
            val breedToBeShown: DogBreedDTO = DogBreedDTO.EMPTY,
            val exception: Throwable? = null
        ) : Result(processStatus)

        data class CheckAnswerResult(
            val isCorrect: Boolean = false,
            val breedCorrectAnswer: DogBreedDTO
        ) : Result(SUCCESS)

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
        val breedOptions: List<DogBreedDTO> = emptyList(),
        val breedCorrectAnswer: DogBreedDTO = DogBreedDTO.EMPTY,
    ) {
        companion object {
            val EMPTY = ViewData()
        }
    }
}
