package com.airwallex.dogquiz.feature.quiz.ui.multiplechoice

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.airwallex.core.designsystem.theme.AirwallexColors
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.theme.LocalNightMode
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.util.fixedStatusBarPadding
import com.airwallex.core.designsystem.util.gradientBackground
import com.airwallex.core.designsystem.util.placeholderShimmer
import com.airwallex.core.designsystem.widget.ButtonSize
import com.airwallex.core.designsystem.widget.QuaternaryButton
import com.airwallex.core.designsystem.widget.TopAppBar
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedDTO
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewData
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceContract.ViewState
import com.airwallex.dogquiz.ui.theme.AppDimens
import com.airwallex.dogquiz.ui.widget.FullScreenLoader
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.ui.component.AsyncImage
import com.airwallex.feature.shared.ui.component.ErrorStateAction
import com.airwallex.feature.shared.ui.component.GenericErrorStateUI
import com.airwallex.feature.shared.ui.component.ScreenProperties
import com.airwallex.feature.shared.ui.component.ThemeScreenContainer
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun MultipleChoiceUI(
    onNav: (ScreenNavAction) -> Unit,
    viewModel: MultipleChoiceViewModel,
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
            is ViewState.Content -> ContentUI(viewModel, viewState.data)
            is ViewState.Error -> GenericErrorStateUI(
                errorStateAction = ErrorStateAction {
                    viewModel.loadData()
                }
            )

            else -> Unit
        }
    }
}

@Composable
private fun ContentUI(
    viewModel: MultipleChoiceViewModel,
    viewData: ViewData
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        viewData.breedCorrectAnswer.imageUrl?.let {
            AsyncImage(
                modifier = Modifier
                    .padding(top = Dimens.Global.MediumLargePadding.get()),
                imgUrl = viewData.breedCorrectAnswer.imageUrl,
                imageModifier = Modifier
                    .size(AppDimens.Quiz.QuizImageSize.get())
                    .clip(shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded),
                contentScale = ContentScale.Crop
            )
        } ?: AsyncImagePlaceholder(
            modifier = Modifier
                .padding(top = Dimens.Global.MediumLargePadding.get())
        )

        Text(
            text = "What breed is this dog?",
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .padding(top = Dimens.Global.LargePadding.get())
                .padding(horizontal = Dimens.Global.LargePadding.get())
        )

        MultipleChoiceUI(
            modifier = Modifier
                .padding(top = Dimens.Global.MediumLargePadding.get())
                .padding(horizontal = Dimens.Global.LargePadding.get()),
            viewData = viewData,
            onChoiceSelected = { selected ->
                viewModel.checkAnswer(
                    breedSelected = selected,
                    breedCorrectAnswer = viewData.breedCorrectAnswer
                )
            }
        )
    }
}

@Composable
private fun MultipleChoiceUI(
    modifier: Modifier = Modifier,
    viewData: ViewData,
    onChoiceSelected: (DogBreedDTO) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        viewData.breedOptions.forEach { option ->
            QuaternaryButton(
                text = option.displayName,
                onClick = { onChoiceSelected(option) },
                size = ButtonSize.NORMAL,
                modifier = Modifier
                    .padding(top = Dimens.Global.MediumPadding.get())
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AsyncImagePlaceholder(
    modifier: Modifier = Modifier,
) =
    Box(
        modifier = modifier
            .size(AppDimens.Quiz.QuizImageSize.get())
            .clip(shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded)
            .placeholderShimmer(true),
    )
