import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

fun Provider<String>.getInt() = get().toInt()

fun Provider<PluginDependency>.getPluginId(): String = get().pluginId

fun <T> tryOrNull(block: () -> T?): T? {
    return try {
        block.invoke()
    } catch (e: Throwable) {
        null
    }
}

fun getSystemEnvOrEmpty(systemEnvName: String): String =
    System.getenv(systemEnvName) ?: ""

fun getSystemEnvOrDefault(systemEnvName: String, defaultValue: String): String =
    System.getenv(systemEnvName) ?: defaultValue

inline fun assert(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        val message = lazyMessage()
        throw AssertionError(message)
    }
}

val String.isBoolean get() = equals("true", true) || equals("false", true)
