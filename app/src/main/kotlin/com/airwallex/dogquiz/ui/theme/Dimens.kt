package com.airwallex.dogquiz.ui.theme

import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.Dimen
import com.airwallex.core.designsystem.theme.DimenGroup
import com.airwallex.core.designsystem.theme.Dimens

object AppDimens : Dimens() {
    object Home : DimenGroup {
        val StartButtonSize = Dimen(mediumDimen = 220.dp)
        val StartButtonRippleSize = Dimen(mediumDimen = 350.dp)
    }

    object Quiz : DimenGroup {
        val QuizImageSize = Dimen(mediumDimen = 300.dp)
        val NextButtonSize = Dimen(mediumDimen = 220.dp)
    }
}


