package config

object ProductFlavor {
    enum class Dimension(val qualifier: String) {
        MODE(qualifier = "mode"),
        ENVIRONMENT(qualifier = "environment");

        companion object {
            fun forEachValue(action: (Dimension) -> Unit) {
                values().forEach(action::invoke)
            }
        }
    }

    enum class Environment(
            val qualifier: String,
            val dimension: Dimension,
            val applicationId: String,
            val signingConfig: SigningConfig
    ) {
        PRODUCTION(
            qualifier = "production",
            dimension = Dimension.ENVIRONMENT,
            applicationId = "com.airwallex.dogquiz",
            signingConfig = SigningConfig.RELEASE
        );

        companion object {
            fun forEachValue(action: (Environment) -> Unit) {
                values().forEach(action::invoke)
            }
        }
    }

    enum class Mode(
        val qualifier: String,
        val dimension: Dimension
    ) {
        STANDARD(
            qualifier = "standard",
            dimension = Dimension.MODE
        );

        companion object {
            fun forEachValue(action: (Mode) -> Unit) {
                values().forEach(action::invoke)
            }
        }
    }
}
