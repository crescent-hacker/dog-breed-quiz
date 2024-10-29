package com.airwallex.feature.shared.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.component.ResponsiveText
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.theme.textTertiary
import com.airwallex.core.designsystem.util.isSmallWidthPhoneScreen
import com.airwallex.core.designsystem.util.resolve
import com.airwallex.core.designsystem.util.stringRes
import com.airwallex.core.designsystem.util.wrap
import com.airwallex.core.designsystem.widget.ButtonSize
import com.airwallex.core.designsystem.widget.CustomSecondaryButton
import com.airwallex.core.util.TextHolder
import com.airwallex.feature.shared.R

@Composable
fun GenericErrorStateUI(
    modifier: Modifier = Modifier,
    title: String = R.string.com_airwallex_feature_shared_error_state_generic_title.stringRes(),
    subtitle: String = R.string.com_airwallex_feature_shared_error_state_generic_subtitle.stringRes(),
    showTitleOnly: Boolean = false,
    errorStateAction: ErrorStateAction? = null,
) = ErrorStateUI(modifier = modifier, title = title, subtitle = subtitle, showTitleOnly = showTitleOnly, errorStateAction = errorStateAction)

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun ErrorStateUI(
    modifier: Modifier = Modifier,
    subtitleModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    title: String,
    subtitle: String = "",
    showTitleOnly: Boolean = false,
    @DrawableRes drawableRes: Int = R.drawable.com_airwallex_feature_shared_ic_lightningbolt,
    errorStateAction: ErrorStateAction? = null,
    fillMaxSize: Boolean = true,
    horizontalPadding: Dp = if (isSmallWidthPhoneScreen()) Dimens.Global.LargePadding.get() else Dimens.Global.XLargePadding.get(),
    maxLines: Int = 1,
    buttonHorizontalPadding: Dp? = null,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    errorButtonSize: ButtonSize = ButtonSize.SMALL
) {
    Column(
        modifier = modifier
            .runIf(fillMaxSize) {
                fillMaxSize()
            }
            .padding(horizontal = horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.Global.SmallPadding.get(), verticalAlignment),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResponsiveText(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Dimens.Global.SmallPadding.get()),
            maxLines = maxLines
        )
        if (subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                modifier = subtitleModifier,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.textTertiary,
                textAlign = TextAlign.Center,
            )
        }
        if (errorStateAction != null) {
            CustomSecondaryButton(
                text = errorStateAction.text.resolve(),
                size = errorButtonSize,
                onClick = { errorStateAction.action.invoke() },
                modifier = buttonModifier.padding(top = Dimens.Global.MediumPadding.get()),
                buttonHorizontalPadding = buttonHorizontalPadding
            )
        }
    }
}

data class ErrorStateAction(
    val text: TextHolder = R.string.com_airwallex_feature_shared_error_retry_button_text.wrap(),
    val action: () -> Unit = {}
)
