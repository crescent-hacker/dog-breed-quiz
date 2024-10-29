package com.airwallex.dogquiz.feature.quiz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.feature.home.navigation.HomeNavGraph
import com.airwallex.dogquiz.feature.home.navigation.HomeNavRoute
import com.airwallex.dogquiz.feature.quiz.data.dto.SatisfactionNavDTO
import com.airwallex.dogquiz.feature.quiz.ui.multiplechoice.MultipleChoiceScreen
import com.airwallex.dogquiz.feature.quiz.ui.satisfaction.SatisfactionScreen
import com.airwallex.dogquiz.navigation.handleAppNavIfNeeded
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.navigation.AbstractFeatureNavGraph
import com.airwallex.feature.shared.navigation.NavRoute
import com.airwallex.feature.shared.navigation.NavRoute.Companion.emptyNavRoute
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.navigation.composable
import com.airwallex.feature.shared.navigation.getArgumentDataObject
import com.airwallex.feature.shared.navigation.navigate
import com.airwallex.feature.shared.navigation.popFullStack
import com.airwallex.feature.shared.navigation.rememberOnNav
import com.airwallex.feature.shared.navigation.toActualNavRouteWithDataObject
import kotlinx.coroutines.CoroutineScope

sealed class QuizNavRoute(
    override val route: String,
    override val args: List<String> = emptyList(),
    override val deeplinks: List<NavDeepLink> = emptyList(),
) : NavRoute(route, args, deeplinks) {
    data object QuizRoot : QuizNavRoute(route = "quiz-root")
    data object MultipleChoice : QuizNavRoute(route = "quiz-multiple-choice")
    data object Satisfaction : QuizNavRoute(route = "quiz-satisfaction/{params}", args = listOf("params"))
}

sealed class QuizNavAction(override val destNavRoute: NavRoute) : ScreenNavAction {
    data class NavigateToSatisfaction(val dto: SatisfactionNavDTO) : QuizNavAction(destNavRoute = QuizNavRoute.Satisfaction)
    data object NavigateToMultipleChoice : QuizNavAction(destNavRoute = QuizNavRoute.MultipleChoice)
}

object QuizNavGraph : AbstractFeatureNavGraph() {
    override val rootRoute: NavRoute = QuizNavRoute.QuizRoot
    override val startRoute: NavRoute = QuizNavRoute.MultipleChoice

    override fun NavGraphBuilder.registerScreens(navController: NavController, appEventBus: EventBus<AppEvent>, scope: CoroutineScope) {
        composable(QuizNavRoute.MultipleChoice) {
            val onNav: (ScreenNavAction) -> Unit =
                rememberOnNav(
                    navController = navController,
                    resolveNavRoute = { action ->
                        when (action) {
                            is QuizNavAction.NavigateToSatisfaction -> action.destNavRoute.toActualNavRouteWithDataObject(action.dto)
                            else -> handleAppNavIfNeeded(action, appEventBus, scope)
                                .emptyNavRoute()
                        }
                    },
                    builder = { action ->
                        when (action) {
                            is NavigateBack -> popUpTo(HomeNavRoute.HomeRoot.route) { inclusive = true }
                        }
                    }
                )

            MultipleChoiceScreen(
                onNav = onNav
            )
        }

        composable(QuizNavRoute.Satisfaction) {
            val dto = QuizNavRoute.Satisfaction.getArgumentDataObject<SatisfactionNavDTO>(it)
            val onNav: (ScreenNavAction) -> Unit =
                rememberOnNav(
                    navController = navController,
                    resolveNavRoute = { action ->
                        when (action) {
                            is QuizNavAction.NavigateToMultipleChoice -> action.destNavRoute
                            else -> handleAppNavIfNeeded(action, appEventBus, scope)
                                .emptyNavRoute()
                        }
                    },
                    builder = { action ->
                        when (action) {
                            is NavigateBack, is QuizNavAction.NavigateToMultipleChoice -> {
                                popUpTo(QuizNavRoute.MultipleChoice.route) { inclusive = true }
                            }
                        }
                    }
                )

            SatisfactionScreen(
                onNav = onNav,
                dto = dto
            )
        }
    }
}
