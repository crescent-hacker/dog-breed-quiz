package com.airwallex.core.designsystem.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.theme.AirwallexColors.Neutrals200
import com.airwallex.core.designsystem.theme.AirwallexColors.Neutrals600
import com.airwallex.core.designsystem.theme.AirwallexColors.PrimaryPurple
import com.airwallex.core.designsystem.theme.AirwallexColors.PrimaryPurple200
import com.airwallex.core.designsystem.theme.AirwallexColors.PrimaryPurple300
import com.airwallex.core.designsystem.theme.AirwallexColors.White
import com.airwallex.core.designsystem.theme.caption2
import com.airwallex.core.designsystem.theme.subtitle4
import com.airwallex.core.designsystem.theme.textPrimary
import com.airwallex.core.designsystem.theme.textTertiary
import com.airwallex.core.designsystem.util.placeholderShimmer
import com.airwallex.core.designsystem.util.resolveDayNightColor

@Composable
fun PrimarySwitch(
    modifier: Modifier = Modifier,
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.subtitle4,
    subtitle: String = "",
    subtitleStyle: TextStyle = MaterialTheme.typography.caption2,
    enabled: Boolean = true,
    isChecked: Boolean = false,
    onCheckChange: (Boolean) -> Unit = {},
    endPadding: Boolean = true,
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .runIf(endPadding) {
                    padding(end = 16.dp)
                },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (title.isNotBlank()) {
                Text(
                    modifier = Modifier.placeholderShimmer(isVisible = isLoading),
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = titleStyle,
                    color = MaterialTheme.colors.textPrimary.let { if (enabled) it else it.copy(alpha = ContentAlpha.disabled) },
                )
            }
            if (subtitle.isNotBlank()) {
                Text(
                    modifier = Modifier.placeholderShimmer(isVisible = isLoading),
                    text = subtitle,
                    style = subtitleStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = MaterialTheme.colors.textTertiary.let { if (enabled) it else it.copy(alpha = ContentAlpha.disabled) },
                )
            }
        }

        Switch(
            modifier = Modifier
                .placeholderShimmer(isVisible = isLoading)
                .runIf(isLoading) {
                    requiredSize(30.dp)
                },
            checked = isChecked,
            onCheckedChange = onCheckChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = resolveDayNightColor(
                    dayColor = PrimaryPurple,
                    nightColor = White
                ),
                checkedTrackColor = resolveDayNightColor(
                    dayColor = PrimaryPurple200,
                    nightColor = PrimaryPurple300
                ),
                checkedTrackAlpha = 1f,
                uncheckedThumbColor = White,
                uncheckedTrackColor = resolveDayNightColor(
                    dayColor = Neutrals200,
                    nightColor = Neutrals600
                ),
                uncheckedTrackAlpha = 1f
            )
        )
    }
}
