package com.airwallex.dogquiz.feature.quiz.ui.satisfaction

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.util.hiltMviViewModel
import com.airwallex.core.designsystem.util.RepeatWhenStarted
import com.airwallex.core.designsystem.util.toast
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionContract.*

@Composable
fun SatisfactionScreen(
    viewModel: SatisfactionViewModel = hiltMviViewModel(),
    onNav: (ScreenNavAction) -> Unit,
    dto: SatisfactionNavDTO
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewState by remember { viewModel.viewState }

    LaunchedEffect(viewState) {
        if (viewState == ViewState.Idle) {
            viewModel.loadData(dto)
        }
    }

    RepeatWhenStarted {
        scope.launch {
            viewModel.viewEffects
                .collectLatest {
                    when (it) {
                        is ViewEffect.NavigateEffect -> onNav(it.navAction)
                        else -> Unit
                    }
                }
        }
    }

    SatisfactionUI(
        ctx = ctx,
        scope = scope,
        onNav = onNav,
        viewModel = viewModel,
        viewState = viewState
    )
}
