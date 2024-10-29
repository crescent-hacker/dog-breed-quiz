package com.airwallex.feature.shared.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.airwallex.feature.shared.navigation.NavRoute
import com.airwallex.feature.shared.ui.base.AbstractMviViewModel
import com.airwallex.core.MviViewState

/**
 * remember MVI view state
 *
 * @param viewModel the mvi viewModel
 * @param confirmResetWhenExitComposition confirm if should reset viewState to initial state when exiting the current composition
 *
 * @return the remembered state of mvi viewState
 */
@Composable
fun <VS : MviViewState> rememberMviViewState(
        viewModel: AbstractMviViewModel<*, VS, *, *>,
        confirmResetWhenExitComposition: Boolean = false
): State<VS> {
    val viewState by rememberUpdatedState(newValue = viewModel.viewState)

    if (confirmResetWhenExitComposition) {
        DisposableEffect(Unit) {
            onDispose {
                viewModel.resetViewState()
            }
        }
    }

    return viewState
}

/**
 * nav route or nav graph scoped hilt view model
 */
@Composable
inline fun <reified VM : AbstractMviViewModel<*, *, *, *>> NavBackStackEntry.navScopedHiltMviViewModel(
        navController: NavController,
        parentRoute: NavRoute
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentRoute.route)
    }

    return hiltMviViewModel(parentEntry)
}

@Composable
inline fun <reified VM : AbstractMviViewModel<*, *, *, *>> hiltMviViewModel(
        viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
        autoInit: Boolean = true
): VM {
    val vm = hiltViewModel<VM>(viewModelStoreOwner)

    if (autoInit) {
        vm.init()
    }

    return vm
}
