package com.airwallex.dogquiz.feature.home.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import com.airwallex.feature.shared.ui.base.AbstractMviViewModel
import javax.inject.Inject
import com.airwallex.dogquiz.feature.home.ui.HomeContract.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contract: HomeContract
) : AbstractMviViewModel<Event, ViewState, ViewEffect, Result>() {
    override fun getInitViewState(): ViewState = ViewState.Idle
    override fun getContract(): HomeContract = contract

    fun loadData(refresh: Boolean = false) {
        dispatchEvent(Event.LoadDataEvent(refresh))
    }

    fun startQuiz() {
        dispatchEvent(Event.StartQuizEvent)
    }
}
