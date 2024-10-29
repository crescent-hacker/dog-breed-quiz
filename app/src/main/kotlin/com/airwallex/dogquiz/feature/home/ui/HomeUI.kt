package com.airwallex.dogquiz.feature.home.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airwallex.core.designsystem.theme.h0
import com.airwallex.core.designsystem.widget.CirclePrimaryButton
import com.airwallex.core.designsystem.widget.Lottie
import com.airwallex.dogquiz.R
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewData
import com.airwallex.dogquiz.feature.home.ui.HomeContract.ViewState
import com.airwallex.dogquiz.ui.theme.AppDimens
import com.airwallex.dogquiz.ui.theme.AppDimens.GlobalDimens
import com.airwallex.dogquiz.ui.widget.FullScreenLoader
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.ui.component.ErrorStateAction
import com.airwallex.feature.shared.ui.component.GenericErrorStateUI
import com.airwallex.feature.shared.ui.component.ThemeScreenContainer
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeUI(
    ctx: Context,
    scope: CoroutineScope,
    onNav: (ScreenNavAction) -> Unit,
    viewModel: HomeViewModel,
    viewState: ViewState
) {
    ThemeScreenContainer {
        when (viewState) {
            is ViewState.Loading -> FullScreenLoader()
            is ViewState.Content -> ContentUI(ctx, scope, viewModel, viewState.data)
            is ViewState.Error -> GenericErrorStateUI(
                errorStateAction = ErrorStateAction { viewModel.loadData(refresh = true) }
            )
            else -> Unit
        }
    }
}

@Composable
private fun ContentUI(
    ctx: Context,
    scope: CoroutineScope,
    viewModel: HomeViewModel,
    viewData: ViewData
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
        ) {
            Text(
                text = "Hi, dog lover.",
                style = MaterialTheme.typography.h0,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(
                    top = GlobalDimens.MediumLargePadding.get(),
                    start = GlobalDimens.MediumPadding.get(),
                    end = GlobalDimens.MediumPadding.get(),
                    bottom = GlobalDimens.SmallPadding.get()
                )
            )

            Text(
                text = "Ready to learn\nmore about",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(
                    horizontal = GlobalDimens.MediumPadding.get()
                )
            )

            Text(
                text = "breeds?",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(
                    horizontal = GlobalDimens.MediumPadding.get()
                )
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Lottie(
                lottieModifier = Modifier.size(AppDimens.Home.StartButtonRippleSize.get()),
                rawLottieRes = R.raw.lottie_ripple,
            )

            CirclePrimaryButton(
                text = "Get started",
                onClick = {
                    viewModel.startQuiz()
                },
                textStyle = MaterialTheme.typography.h3,
                modifier = Modifier
                    .size(AppDimens.Home.StartButtonSize.get())
            )
        }

    }
}
