package com.airwallex.core.designsystem.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import kotlin.math.roundToInt

private class OverlayImagePainter constructor(
    private val image: ImageBitmap,
    private val imageOverlays: List<Pair<ImageBitmap, IntOffset>>,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height),
) : Painter() {

    private val size: IntSize = validateSize(srcOffset, srcSize)
    override fun DrawScope.onDraw() {
        // draw the first image without any blend mode
        drawImage(
            image,
            srcOffset,
            srcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            )
        )
        imageOverlays.forEach { (imageOverlay, offset) ->
            val overlaySize = IntSize(imageOverlay.width, imageOverlay.height)
            this@onDraw.drawImage(
                image = imageOverlay,
                srcOffset = srcOffset,
                dstOffset = offset,
                srcSize = overlaySize,
                dstSize = IntSize(
                    this@onDraw.size.width.roundToInt(),
                    this@onDraw.size.height.roundToInt()
                ),
                blendMode = BlendMode.Overlay
            )
        }
    }

    /**
     * Return the dimension of the underlying [ImageBitmap] as it's intrinsic width and height
     */
    override val intrinsicSize: Size get() = size.toSize()

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= image.width &&
                    srcSize.height <= image.height
        )
        return srcSize
    }
}

@Composable
fun CollageImage(
    baseImage: ImageBitmap,
    imageOverlays: List<Pair<ImageBitmap, IntOffset>>,
) {
    val customPainter = remember {
        OverlayImagePainter(baseImage, imageOverlays)
    }
    Image(
        painter = customPainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.wrapContentSize()
    )
}
