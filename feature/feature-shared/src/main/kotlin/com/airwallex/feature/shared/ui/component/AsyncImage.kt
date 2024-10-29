package com.airwallex.feature.shared.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airwallex.core.designsystem.util.placeholderShimmer

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    @DrawableRes fallbackIconRes: Int = 0,
    imgUrl: String,
    onLoading: (AsyncImagePainter.State.Loading) -> Unit = {},
    onError: (AsyncImagePainter.State.Error) -> Unit = {},
    onSuccess: (AsyncImagePainter.State.Success) -> Unit = {},
    contentScale: ContentScale = ContentScale.Fit,
) {
    Box(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.align(Alignment.Center),
            onLoading = onLoading,
            onError = onError,
            onSuccess = onSuccess,
            loading = {
                Box(
                    modifier = Modifier
                        .composed { imageModifier }
                        .placeholderShimmer(isVisible = true)
                )
            },
            success = {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .composed { imageModifier },
                )
            },
            error = {
                Image(
                    painter = painterResource(id = fallbackIconRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                        .align(Alignment.Center),
                )
            },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            filterQuality = FilterQuality.High
        )
    }
}
