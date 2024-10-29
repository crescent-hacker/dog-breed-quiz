package com.airwallex.dogquiz.feature.quiz.ui.satisfaction

import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.navigation.QuizNavAction
import dagger.hilt.android.lifecycle.HiltViewModel
import com.airwallex.feature.shared.ui.base.AbstractMviViewModel
import javax.inject.Inject
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionContract.*
import com.airwallex.feature.shared.navigation.NavigateBack

@HiltViewModel
class SatisfactionViewModel @Inject constructor(
    private val contract: SatisfactionContract
) : AbstractMviViewModel<Event, ViewState, ViewEffect, Result>() {
    override fun getInitViewState(): ViewState = ViewState.Idle
    override fun getContract(): SatisfactionContract = contract

    fun loadData(dto: SatisfactionNavDTO) {
        dispatchEvent(Event.LoadDataEvent(dto))
    }

    fun navigateToNextQuestion() {
        dispatchSideEffect(ViewEffect.NavigateEffect(QuizNavAction.NavigateToMultipleChoice))
    }
}
