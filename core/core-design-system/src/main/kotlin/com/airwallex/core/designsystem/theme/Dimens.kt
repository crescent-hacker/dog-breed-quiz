package com.airwallex.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.util.LocalWindowSizeClass
import com.airwallex.core.designsystem.util.WindowSizeClass.WindowType.*

/**
 * define dimension for different phone/tablet size
 *
 * TODO, will support tablet in the future, currently only for phone
 */
data class Dimen(
        /**
         * medium phone size dimen
         */
        private val mediumDimen: Dp,
        /**
         * large phone size dimen
         */
        private val largeDimen: Dp = mediumDimen,

        /**
         * small phone size dimen
         */
        private val smallDimen: Dp = mediumDimen
) {
    /**
     * Require window size provided in CompositionLocal
     */
    @Composable
    fun get(): Dp =
            when (LocalWindowSizeClass.current.widthWindowSizeClass) {
                CompactHandsetPortraitSmall -> smallDimen
                CompactHandsetPortraitMedium -> mediumDimen
                CompactHandsetPortraitLarge -> largeDimen
                else -> largeDimen
            }
}

/**
 * holder for dimen definitions for all device size
 */
open class Dimens {
    val GlobalDimens = Global

    /**
     * this class only includes general dimens
     *
     * @note for feature module or app module specific dimens should extend this class
     */
    object Global: DimenGroup {
        /**
         * 72 dp
         */
        val XXLargePadding = Dimen(72.dp)
        /**
         * 64 dp
         */
        val XLargePadding = Dimen(64.dp)
        /**
         * 32 dp
         */
        val LargePadding = Dimen(32.dp)
        /**
         * 24 dp
         */
        val MediumLargePadding = Dimen(24.dp)

        /**
         * 16 dp
         */
        val MediumPadding = Dimen(16.dp)
        /**
         * 12 dp
         */
        val MediumSmallPadding = Dimen(12.dp)
        /**
         * 8 dp
         */
        val SmallPadding = Dimen(8.dp)
        /**
         * 4 dp
         */
        val ExtraSmallPadding = Dimen(4.dp)
        /**
         * 48 dp
         */
        val MinTouchTarget = Dimen(48.dp)
    }
}

interface DimenGroup
