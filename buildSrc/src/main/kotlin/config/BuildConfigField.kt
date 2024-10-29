package config

import com.android.build.api.dsl.DefaultConfig
import getSystemEnvOrDefault

/*************************************************************************
 *                         Build Config Field - definition               *
 *************************************************************************/
val MVI_LOGGING = BuildConfigField("boolean", "MVI_LOGGING", "false")
val NAVIGATION_LOGGING = BuildConfigField("boolean", "NAVIGATION_LOGGING", "false")
val BRAZE_LOGGING_LEVEL = BuildConfigField("String", "BRAZE_LOGGING_LEVEL", "\"\"")
val OKHTTPCLIENT_VERBOSE_LOG_ENABLED = BuildConfigField("boolean", "OKHTTPCLIENT_VERBOSE_LOG_ENABLED", "true")

val FEATURES_ALL_ENABLED = BuildConfigField("boolean", "FEATURES_ALL_ENABLED", "false")

/*************************************************************************
 *                       Build Config Field - misc helpers               *
 *************************************************************************/
const val FEATURE_FLAG_PROPERTY_PREFIX = "feature."
data class BuildConfigField(
    val type: String, // could be primitive type like "boolean", "String", etc
    val name: String,
    val defaultValue: String,
) {
    fun getValueFromSystemEnv(): String =
        getSystemEnvOrDefault(name, defaultValue)

    fun getBooleanValueFromSystemEnv(): Boolean {
        assert(type == "boolean") {
            "The default type of $name must be boolean instead of $type"
        }

        return getSystemEnvOrDefault(name, defaultValue).toBoolean()
    }
}

fun DefaultConfig.buildConfigFieldFromSystemEnv(field: BuildConfigField) {
    buildConfigField(
        field.type,
        field.name,
        field.getValueFromSystemEnv()
    )
}

