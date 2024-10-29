package com.airwallex.dogquiz.feature.quiz.ui.multiplechoice

import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedDTO
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.navigation.QuizNavAction
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.Event
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.Result
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewEffect
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewState
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.ui.base.AbstractMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MultipleChoiceViewModel @Inject constructor(
    private val contract: MultipleChoiceContract
) : AbstractMviViewModel<Event, ViewState, ViewEffect, Result>() {
    override fun getInitViewState(): ViewState = ViewState.Idle
    override fun getContract(): MultipleChoiceContract = contract

    fun loadData() {
        dispatchEvent(Event.LoadDataEvent)
    }

    fun checkAnswer(breedSelected: DogBreedDTO, breedCorrectAnswer: DogBreedDTO) {
        dispatchEvent(Event.CheckAnswerEvent(
            breedSelected = breedSelected,
            breedCorrectAnswer = breedCorrectAnswer
        ))
    }
}
