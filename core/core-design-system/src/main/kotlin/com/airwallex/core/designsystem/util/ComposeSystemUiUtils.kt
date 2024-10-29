package com.airwallex.core.designsystem.util

import android.annotation.SuppressLint
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.airwallex.core.designsystem.theme.LocalNightMode
import com.airwallex.core.designsystem.widget.AppBarHeight
import com.airwallex.core.util.getActivity

/*******************************************************************
 *                        system bar operations                    *
 *******************************************************************/

@Composable
fun setupModalSystemBarColor(
    systemUiController: SystemUiController = rememberSystemUiController(),
    systemBarBackgroundWhenEnter: Color = MaterialTheme.colors.surface,
    systemBarBackgroundWhenExit: Color = Color.Unspecified
) {
    setupBottomNavBarColor(systemUiController, systemBarBackgroundWhenEnter.copy(0.1f), systemBarBackgroundWhenExit)
}

@Composable
fun setupSystemBarColor(
    systemUiController: SystemUiController = rememberSystemUiController(),
    systemBarBackgroundWhenEnter: Color = MaterialTheme.colors.background,
    systemBarBackgroundWhenExit: Color = Color.Unspecified,
    enterDelay: Long = 230L,
    exitDelay: Long = 0L
) {
    val useDarkIcons = !LocalNightMode.current
    val scope = rememberCoroutineScope()

    DisposableEffect(systemUiController, useDarkIcons, systemBarBackgroundWhenEnter) {
        scope.launch {
            if (enterDelay > 0)
                delay(enterDelay)
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setSystemBarsColor(
                color = systemBarBackgroundWhenEnter,
                darkIcons = useDarkIcons
            )
        }

        // setStatusBarColor() and setNavigationBarColor() also exist
        onDispose {
            if (systemBarBackgroundWhenExit != Color.Unspecified) {
                scope.launch {
                    if (exitDelay > 0)
                        delay(exitDelay)
                    systemUiController.setSystemBarsColor(
                        color = systemBarBackgroundWhenExit,
                        darkIcons = useDarkIcons
                    )
                }
            }
        }
    }
}

@Composable
fun setupBottomNavBarColor(
    systemUiController: SystemUiController = rememberSystemUiController(),
    navBarBackgroundWhenEnter: Color = MaterialTheme.colors.background,
    navBarBackgroundWhenExit: Color = Color.Unspecified,
    enterDelay: Long = 230L,
    exitDelay: Long = 0L
) {
    val useDarkIcons = !LocalNightMode.current
    val scope = rememberCoroutineScope()

    DisposableEffect(systemUiController, useDarkIcons, navBarBackgroundWhenEnter) {
        scope.launch {
            if (enterDelay > 0)
                delay(enterDelay)
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setNavigationBarColor(
                color = navBarBackgroundWhenEnter,
                darkIcons = useDarkIcons
            )
        }

        // setStatusBarColor() and setNavigationBarColor() also exist
        onDispose {
            if (navBarBackgroundWhenExit != Color.Unspecified) {
                scope.launch {
                    if (exitDelay > 0)
                        delay(exitDelay)
                    systemUiController.setNavigationBarColor(
                        color = navBarBackgroundWhenExit,
                        darkIcons = useDarkIcons
                    )
                }
            }
        }
    }
}

@Composable
fun setupStatusBarColor(
    systemUiController: SystemUiController = rememberSystemUiController(),
    statusBarBackgroundWhenEnter: Color = MaterialTheme.colors.background,
    statusBarBackgroundWhenExit: Color = Color.Unspecified,
    enterDelay: Long = 230L,
    exitDelay: Long = 0L
) {
    val useDarkIcons = !LocalNightMode.current
    val scope = rememberCoroutineScope()

    DisposableEffect(systemUiController, useDarkIcons, statusBarBackgroundWhenEnter) {
        scope.launch {
            if (enterDelay > 0)
                delay(enterDelay)
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setStatusBarColor(
                color = statusBarBackgroundWhenEnter,
                darkIcons = useDarkIcons
            )
        }

        // setStatusBarColor() and setNavigationBarColor() also exist
        onDispose {
            if (statusBarBackgroundWhenExit != Color.Unspecified) {
                scope.launch {
                    if (exitDelay > 0)
                        delay(exitDelay)
                    systemUiController.setStatusBarColor(
                        color = statusBarBackgroundWhenExit,
                        darkIcons = useDarkIcons
                    )
                }
            }
        }
    }
}

/******************************************************************************
 *      fixed system bar padding modifier ext (used by bottom sheet modal)    *
 ******************************************************************************/

fun Modifier.fixedStatusBarPadding(): Modifier = composed {
    val act = getActivity()
    padding(top = act.getSystemBarPaddings().statusBarPadding)
}

fun Modifier.fixedNavigationBarPadding(): Modifier = composed {
    val act = getActivity()
    padding(bottom = act.getSystemBarPaddings().navigationBarPadding)
}

fun Modifier.fixedSystemBarsPadding(): Modifier = composed {
    val act = getActivity()
    padding(
        top = act.getSystemBarPaddings().statusBarPadding,
        bottom = act.getSystemBarPaddings().navigationBarPadding
    )
}


/*******************************************************************
 *                        helpers                                  *
 *******************************************************************/

@SuppressLint("ComposableNaming")
@Composable
fun requestImmersiveFullScreen() {
    val view = LocalView.current
    // !! should be safe here since the view is part of an Activity
    val window = view.context.getActivity()!!.window
    val controller = WindowCompat.getInsetsController(window, view)
    controller.apply {
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controlWindowInsetsAnimation(WindowInsetsCompat.Type.systemBars(), 0, AccelerateDecelerateInterpolator(), null, WindowInsetsAnimationControlListener)
        hide(WindowInsetsCompat.Type.systemBars())
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun exitImmersiveFullScreen() {
    val view = LocalView.current
    // !! should be safe here since the view is part of an Activity
    val window = view.context.getActivity()!!.window
    val controller = WindowCompat.getInsetsController(window, view)
    controller.apply {
        controlWindowInsetsAnimation(WindowInsetsCompat.Type.systemBars(), 0, AccelerateDecelerateInterpolator(), null, WindowInsetsAnimationControlListener)
        show(WindowInsetsCompat.Type.systemBars())
    }
}

private object WindowInsetsAnimationControlListener : WindowInsetsAnimationControlListenerCompat {
    override fun onReady(controller: WindowInsetsAnimationControllerCompat, types: Int) {}
    override fun onFinished(controller: WindowInsetsAnimationControllerCompat) {}
    override fun onCancelled(controller: WindowInsetsAnimationControllerCompat?) {}
}

fun Modifier.topAppBarPadding(): Modifier = composed {
    padding(
        top = AppBarHeight
    )
}

fun Modifier.edgeOffset(): Modifier = composed {
    layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints.copy(
                maxWidth = constraints.maxWidth + 16.dp.roundToPx(),
            )
        )
        layout(placeable.width, placeable.height) {
            placeable.place(8.dp.roundToPx(), 0)
        }
    }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }
