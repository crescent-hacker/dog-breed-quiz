package com.airwallex.dogquiz.feature.quiz.ui.satisfaction

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.component.ResponsiveText
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.extendedColors
import com.airwallex.core.designsystem.util.fixedStatusBarPadding
import com.airwallex.core.designsystem.util.gradientBackground
import com.airwallex.core.designsystem.widget.CirclePrimaryButton
import com.airwallex.core.designsystem.widget.Lottie
import com.airwallex.core.designsystem.widget.QuaternaryButton
import com.airwallex.core.designsystem.widget.SecondaryButton
import com.airwallex.core.designsystem.widget.TopAppBar
import com.airwallex.dogquiz.R
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceUI
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionContract.ViewData
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionContract.ViewState
import com.airwallex.dogquiz.ui.theme.AppDimens
import com.airwallex.dogquiz.ui.widget.FullScreenLoader
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.ui.component.AsyncImage
import com.airwallex.feature.shared.ui.component.GenericErrorStateUI
import com.airwallex.feature.shared.ui.component.ScreenProperties
import com.airwallex.feature.shared.ui.component.ThemeScreenContainer
import kotlinx.coroutines.CoroutineScope

@Composable
fun SatisfactionUI(
    ctx: Context,
    scope: CoroutineScope,
    onNav: (ScreenNavAction) -> Unit,
    viewModel: SatisfactionViewModel,
    viewState: ViewState
) {
    ThemeScreenContainer(
        modifier = Modifier.gradientBackground(baseColor = MaterialTheme.colors.primary),
        screenProperties = ScreenProperties(
            showUnderSystemBar = true
        ),
    ) {
        TopAppBar(
            modifier = Modifier.fixedStatusBarPadding(),
            onNavIconClicked = { onNav(NavigateBack) },
            appBarBackgroundColor = Color.Transparent,
        )

        when (viewState) {
            is ViewState.Loading -> FullScreenLoader()
            is ViewState.Content -> ContentUI(ctx, scope, viewModel, viewState.data)
            is ViewState.Error -> GenericErrorStateUI()
            else -> Unit
        }
    }
}

@Composable
private fun ContentUI(
    ctx: Context,
    scope: CoroutineScope,
    viewModel: SatisfactionViewModel,
    viewData: ViewData
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Global.MediumPadding.get()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopImageUI(viewData = viewData)

                if (viewData.isSuccess) {
                    CorrectAnswerTextUI(viewData = viewData)
                } else {
                    IncorrectAnswerTextUI(viewData = viewData)
                }
            }

            QuaternaryButton(
                text = "Next",
                onClick = {
                    viewModel.navigateToNextQuestion()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.Global.XLargePadding.get())
            )
        }
    }
}

@Composable
private fun TopImageUI(
    viewData: ViewData
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .padding(top = Dimens.Global.MediumLargePadding.get()),
            imgUrl = viewData.imageUrl,
            imageModifier = Modifier
                .size(AppDimens.Quiz.QuizImageSize.get())
                .clip(shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded),
            contentScale = ContentScale.Crop
        )

        if (viewData.isSuccess) {
            Lottie(
                modifier = Modifier
                    .padding(top = Dimens.Global.MediumLargePadding.get())
                    .size(AppDimens.Quiz.QuizImageSize.get())
                    .clip(shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded)
                    .alpha(0.8f),
                rawLottieRes = R.raw.lottie_tick,
                iterations = 1,
            )
        }
    }
}

@Composable
private fun CorrectAnswerTextUI(
    viewData: ViewData
) {
    Text(
        text = "Correct!",
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.extendedColors.success,
        modifier = Modifier.padding(top = Dimens.Global.LargePadding.get())
    )
    ResponsiveText(
        text = "This is a ${viewData.breedName}.",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(top = Dimens.Global.SmallPadding.get())
    )
}

@Composable
private fun IncorrectAnswerTextUI(viewData: ViewData) {
    Text(
        text = "No luck this time.",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(top = Dimens.Global.LargePadding.get())
    )
    ResponsiveText(
        text = "This is a ${viewData.breedName}.",
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(top = Dimens.Global.SmallPadding.get())
    )
}
