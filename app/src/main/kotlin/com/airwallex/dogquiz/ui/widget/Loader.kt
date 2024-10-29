package com.airwallex.dogquiz.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.extendedColors
import com.airwallex.core.designsystem.widget.Loader
import com.airwallex.dogquiz.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun FullScreenLoader(
    modifier: Modifier = Modifier,
    onComplete: suspend CoroutineScope.() -> Unit = {},
) {
    Loader(
        modifier = modifier
            .fillMaxSize()
        ,
        lottieModifier = Modifier.size(200.dp),
        rawLottieRes = R.raw.lottie_dog_walking,
        onComplete = onComplete
    )
}