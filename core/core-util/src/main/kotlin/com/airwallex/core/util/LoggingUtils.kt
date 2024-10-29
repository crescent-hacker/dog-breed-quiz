package com.airwallex.core.util

import android.util.Log
import java.util.logging.Logger

const val LOGGER_TAG = "AirwallexAndroidApp"

lateinit var AppLogger: AirwallexLogger

fun getLogger(tag: String, env: String, isDebug: Boolean): AirwallexLogger = AirwallexLoggerImpl(tag = tag, env = env, isDebug = isDebug)

interface AirwallexLogger {
    fun verbose(
        message: String,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun debug(
        message: String,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun info(
        message: String,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun warn(
        message: String,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun error(
        message: String,
        throwable: Throwable? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun error(
        throwable: Throwable,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun wtf(
        message: String,
        attributes: Map<String, Any?> = emptyMap()
    )
}

fun AirwallexLogger.nonFatalError(
    throwable: Throwable
) = ErrorReportUtils.logNonFatalException(throwable = throwable, logger = this)

class AirwallexLoggerImpl(
    private val tag: String,
    private val isDebug: Boolean,
    env: String
) : AirwallexLogger {
    private val serviceName = "/android/$env".lowercase()

    /**
     * customized logcat logger
     */
    private val logcatLogger by lazy {
        LogcatLogger(serviceName = serviceName, isDebug)
    }

    override fun verbose(message: String, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.VERBOSE, message)
    }

    override fun debug(message: String, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.DEBUG, message)
    }

    override fun info(message: String, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.INFO, message)
    }

    override fun warn(message: String, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.WARN, message)
    }

    override fun error(message: String, throwable: Throwable?, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.ERROR, message, throwable)
    }

    override fun error(throwable: Throwable, attributes: Map<String, Any?>) {
        val message = throwable.message ?: "No message"
        logcatLogger.log(Log.ERROR, message, throwable)
    }

    override fun wtf(message: String, attributes: Map<String, Any?>) {
        logcatLogger.log(Log.ASSERT, message)
    }
}

/**
 * modified from [com.datadog.android.log.internal.logger.LogcatLogHandler]
 */
private class LogcatLogger(
    private val serviceName: String,
    private val isDebug: Boolean
) {
    fun log(
        level: Int,
        message: String,
        throwable: Throwable? = null,
    ) {
        val stackElement = getCallerStackElement()
        val tag = resolveTag(stackElement)
        val suffix = if (level > Log.DEBUG) resolveSuffix(stackElement) else ""
        Log.println(level, tag, message + suffix)
        if (throwable != null) {
            Log.println(
                level,
                tag,
                Log.getStackTraceString(throwable)
            )
        }
    }

    private fun resolveTag(stackTraceElement: StackTraceElement?): String {
        val tag = stackTraceElement?.className?.replace(ANONYMOUS_CLASS, "")?.substringAfterLast('.')?.substringBeforeLast("$") ?: serviceName
        return if (tag.length >= MAX_TAG_LENGTH) {
            @Suppress("UnsafeThirdPartyFunctionCall")
            // substring can't throw IndexOutOfBounds, we checked the length
            tag.substring(0, MAX_TAG_LENGTH)
        } else {
            tag
        }
    }

    private fun resolveSuffix(stackTraceElement: StackTraceElement?): String {
        return if (stackTraceElement == null) {
            ""
        } else {
            "\t| at .${stackTraceElement.methodName}" +
                    "(${stackTraceElement.fileName}:${stackTraceElement.lineNumber})"
        }
    }

    fun getCallerStackElement(): StackTraceElement? {
        return if (isDebug) {
            val stackTrace = Throwable().stackTrace
            return findValidCallStackElement(stackTrace)
        } else {
            null
        }
    }

    fun findValidCallStackElement(
        stackTrace: Array<StackTraceElement>
    ): StackTraceElement? {
        return stackTrace.firstOrNull { element ->
            element.className !in IGNORED_CLASS_NAMES &&
                    IGNORED_PACKAGE_PREFIXES.none { element.className.startsWith(it) }
        }
    }

    companion object {

        private const val MAX_TAG_LENGTH = 23

        private val ANONYMOUS_CLASS = Regex("(\\$\\d+)+$")

        // internal for testing
        internal val IGNORED_CLASS_NAMES = arrayOf(
            Logger::class.java.canonicalName,
            LogcatLogger::class.java.canonicalName,
        )

        // internal for testing
        internal val IGNORED_PACKAGE_PREFIXES = arrayOf(
            "com.datadog.android.timber",
            "com.airwallex.core.util.LogcatLogger",
            "com.airwallex.core.util.AirwallexLogger",
            "timber.log"
        )
    }
}

fun String.getAndroidLogLevel(noneLogLevel: Int): Int {
    return when (this) {
        "VERBOSE" -> Log.VERBOSE
        "WARN" -> Log.WARN
        "DEBUG" -> Log.DEBUG
        "ERROR" -> Log.ERROR
        "INFO" -> Log.INFO
        else -> noneLogLevel
    }
}
