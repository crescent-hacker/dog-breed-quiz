package com.airwallex.dogquiz.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.airwallex.core.designsystem.util.RepeatWhenStarted
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewEffect
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewState
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.util.hiltMviViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltMviViewModel(),
    onNav: (ScreenNavAction) -> Unit
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewState by remember { viewModel.viewState }

    LaunchedEffect(viewState) {
        if (viewState == ViewState.Idle) {
            viewModel.loadData()
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

    HomeUI(
        ctx = ctx,
        scope = scope,
        onNav = onNav,
        viewModel = viewModel,
        viewState = viewState
    )
}
