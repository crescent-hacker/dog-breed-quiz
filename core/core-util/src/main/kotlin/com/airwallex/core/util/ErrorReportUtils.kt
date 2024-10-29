package com.airwallex.core.util

object ErrorReportUtils {
    private var errorReporter: ErrorReporter? = null

    fun init(errorReporter: ErrorReporter) {
        this.errorReporter = errorReporter
    }

    fun logNonFatalException(throwable: Throwable, tag: String = "ErrorReporter", logger: AirwallexLogger) {
        errorReporter?.reportNonFatalException(throwable, tag, logger)
    }
}

interface ErrorReporter {
    fun reportNonFatalException(e: Throwable, tag: String? = null, logger: AirwallexLogger)
}

object DefaultErrorReporterImpl : ErrorReporter {
    override fun reportNonFatalException(e: Throwable, tag: String?, logger: AirwallexLogger) =
        logger.error(message = e.message ?: "", throwable = e)
}
