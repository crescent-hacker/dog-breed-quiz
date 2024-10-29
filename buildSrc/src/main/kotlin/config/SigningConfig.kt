package config

import getSystemEnvOrEmpty

enum class SigningConfig(
        val qualifier: String,
        val keyAlias: String,
        val keyPassword: String,
        val storeFilePath: String,
        val storePassword: String,
        val skipErrors: Boolean = false
) {
    // TODO, keystore file and password should be stored in CI/CD secret and injected as env variable, plain text here is for demo purpose
    RELEASE(
            qualifier = "release",
            keyAlias = "dog_quiz_android_release_key",
            keyPassword = "test1234",
            storeFilePath = "keystores/release.jks",
            storePassword = "test1234",
            skipErrors = true
    ),

    DEBUG(
            qualifier = "debug",
            keyAlias = "dog_quiz_android_debug_key",
            keyPassword = "test1234",
            storeFilePath = "keystores/debug.jks",
            storePassword = "test1234"
    );

    companion object {
        fun forEachValue(action: (SigningConfig) -> Unit) {
            values().forEach(action::invoke)
        }
    }
}
