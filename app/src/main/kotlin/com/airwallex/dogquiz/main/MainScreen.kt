package com.airwallex.dogquiz.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.airwallex.core.designsystem.util.linkAllTo
import com.airwallex.core.designsystem.util.setupSystemBarColor
import com.airwallex.dogquiz.navigation.AppNavGraph

/**
 * Entry point screen of the whole app
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // setup system bar with no delay as this is the very first screen before showing verify-auth screen
    setupSystemBarColor(enterDelay = 0)

    Surface(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (navGraph) = createRefs()
            AppNavGraph(
                modifier = Modifier
                    .constrainAs(navGraph) {
                        linkAllTo(parent)
                    },
                navController = navController,
            )
        }
    }
}
