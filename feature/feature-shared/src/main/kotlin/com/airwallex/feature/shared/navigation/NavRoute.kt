package com.airwallex.feature.shared.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.airwallex.feature.shared.error.NavArgNotFoundException
import com.airwallex.core.util.decodeBase64UrlSafeString
import com.airwallex.core.util.encodeBase64UrlSafeString
import com.airwallex.core.util.json.kotlinJson
import com.airwallex.core.util.tryOrNull
import java.util.Optional

@Serializable
data class BottomNavDisplayInfo(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
)

open class NavRoute(
    open val route: String,
    open val args: List<String> = emptyList(),
    open val deeplinks: List<NavDeepLink> = emptyList(),
) {
    companion object {
        val EMPTY = NavRoute(route = "", args = emptyList(), deeplinks = emptyList())
        val BACK = NavRoute(route = "back", args = emptyList(), deeplinks = emptyList())

        fun deserializeFromString(serializedString: String): NavRoute {
            return kotlinJson.decodeFromString<SerializableNavRoute>(serializedString).let {
                NavRoute(
                    route = it.route,
                    args = it.args,
                )
            }
        }

        fun serializeToString(navRoute: NavRoute): String {
            return kotlinJson.encodeToString(
                SerializableNavRoute(
                    route = navRoute.route,
                    args = navRoute.args,
                )
            )
        }

        fun Any.emptyNavRoute(): NavRoute = this.run { EMPTY }

        /**
         * a shadow class for [NavRoute] serialization
         */
        @Serializable
        data class SerializableNavRoute(
            val route: String,
            val args: List<String>,
            val bottomNavDisplayInfo: BottomNavDisplayInfo? = null
        )
    }
}

/**
 * convert to a new NavRoute which the route string's path arguments are replaced by the actual values
 * this function is needed to be called when a route with path args is about to be used by [NavController.navigate]
 */
fun NavRoute.toActualNavRoute(vararg pathArgValues: String): NavRoute {
    var actualRoute = route

    assert(pathArgValues.size == args.size) {
        "The number of path argument values must be equal to the number of path arguments in route: $route"
    }
    args.forEachIndexed { index, argKey ->
        actualRoute = actualRoute.replace("{$argKey}", pathArgValues[index])
    }

    return NavRoute(route = actualRoute, args = this.args, deeplinks = this.deeplinks)

}


inline fun <reified T> NavRoute.toActualNavRouteWithDataObject(data: T): NavRoute {
    var actualRoute = route

    assert(args.size == 1) {
        "Only one route path argument is allowed when using NavRoute.toActualNavRoute(data: T)"
    }

    val argKey = args[0]

    actualRoute = actualRoute.replace("{$argKey}", Json.encodeToString(data).encodeBase64UrlSafeString())

    return NavRoute(route = actualRoute, args = this.args, deeplinks = this.deeplinks)
}

/**
 * get Arguments from NavRoute
 */
fun NavRoute.getArguments(backStackEntry: NavBackStackEntry): List<String> =
    this.args.map { argKey ->
        backStackEntry.arguments?.getString(argKey) ?: throw NavArgNotFoundException
    }

/**
 * get object argument from NavRoute
 */
inline fun <reified T> NavRoute.getArgumentDataObject(backStackEntry: NavBackStackEntry): T {
    assert(args.size == 1) {
        "Only one route path argument is allowed when using NavRoute.getArguments(backStackEntry: NavBackStackEntry): T"
    }

    val argKey = args[0]

    val encodedArgValue = backStackEntry.arguments?.getString(argKey) ?: throw NavArgNotFoundException

    val base64DecodedValue = encodedArgValue.decodeBase64UrlSafeString()

    return Json.decodeFromString(base64DecodedValue)
}

inline fun <reified T : Any> NavRoute.getOptionalArgumentDataObject(backStackEntry: NavBackStackEntry): Optional<T> {
    assert(args.size == 1) {
        "Only one route path argument is allowed when using NavRoute.getArguments(backStackEntry: NavBackStackEntry): T"
    }

    return tryOrNull {
        Optional.of(getArgumentDataObject(backStackEntry))
    } ?: Optional.empty<T>()
}
