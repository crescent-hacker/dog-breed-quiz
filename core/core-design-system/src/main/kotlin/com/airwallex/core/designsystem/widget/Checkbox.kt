package com.airwallex.core.designsystem.widget

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.R
import com.airwallex.core.designsystem.theme.*

enum class CheckboxSize {
    NORMAL, LARGE
}

@Composable
fun PrimaryCheckbox(
    modifier: Modifier = Modifier,
    size: CheckboxSize = CheckboxSize.NORMAL,
    checkedBoxColor: Color = MaterialTheme.colors.checkedBox,
    uncheckedBoxColor: Color = uncheckedBox,
    checkmarkColor: Color = MaterialTheme.colors.checkmark,
    uncheckedBorderColor: Color = MaterialTheme.colors.uncheckedBorderColor,
    checkedBorderColor: Color = MaterialTheme.colors.checkedBorderColor,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    val sizeDp = when (size) {
        CheckboxSize.NORMAL -> 20.dp
        CheckboxSize.LARGE -> 40.dp
    }
    val iconPadding = when (size) {
        CheckboxSize.NORMAL -> 2.dp
        CheckboxSize.LARGE -> 4.dp
    }
    var isChecked by remember { mutableStateOf(checked) }
    val currentBoxColor: Color by animateColorAsState(if (isChecked) checkedBoxColor else uncheckedBoxColor)
    val currentBorderColor: Color by animateColorAsState(if (isChecked) checkedBorderColor else uncheckedBorderColor)

    Row(
        modifier = modifier
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = {
                    isChecked = !isChecked
                    onCheckedChange.invoke(isChecked)
                }
            )
    ) {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(2.dp, color = currentBorderColor),
        ) {
            Box(
                modifier = Modifier
                    .size(sizeDp)
                    .background(currentBoxColor),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isChecked,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(.6f)
                            .padding(top = iconPadding),
                        painter = painterResource(id = R.drawable.com_airwallex_core_designsystem_ic_check),
                        contentDescription = null,
                        tint = checkmarkColor
                    )
                }
            }
        }
    }
}
