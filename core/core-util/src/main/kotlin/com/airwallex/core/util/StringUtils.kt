package com.airwallex.core.util

import android.content.Context
import android.util.Base64
import androidx.annotation.StringRes
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.Normalizer
import java.util.Locale

const val BYTE_TO_STRING_SEPARATOR = ","

fun String.safeSubstring(start: Int, end: Int): String {
    return tryOrNull {
        this.substring(start, end)
    } ?: ""
}

fun String.safeSubstring(start: Int): String {
    return tryOrNull {
        this.substring(start)
    } ?: ""
}

val String.digits get() = this.filter { it.isDigit() }

fun String.sentenceCase() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun String.titleCase() = split(" ").joinToString(" ") { if (it.lowercase() != "and") it.sentenceCase() else it }

fun String.camelCase(): String {
    val words = split("_", " ", "-") // split by underscore, space, or hyphen
    val camelCaseWords = words.mapIndexed { index, word ->
        if (index == 0) word.lowercase() else word.replaceFirstChar { it.uppercase() }
    }
    return camelCaseWords.joinToString("")
}

fun String.isOnlyLetters() = all { it.isLetter() }

fun String.stripWhitespace() = replace(" ", "")

/***********************************************************************
 *                            TextHolder                               *
 ***********************************************************************/

/**
 * Text holder - wrap either string or string resource
 */
data class TextHolder(
    @StringRes val textRes: Int = 0,
    val text: String = ""
) {
    companion object {
        val EMPTY = TextHolder(0, "")
    }

    fun isEmpty() = this == EMPTY

    fun isBlank() = isEmpty() || (this.text.isBlank())
}

fun TextHolder.resolveOrNull(ctx: Context): String? =
    textRes.takeIf { it != 0 }?.let { ctx.getString(it) }
        ?: text.takeIf { it.isNotEmpty() }

fun TextHolder.resolve(ctx: Context): String =
    textRes.takeIf { it != 0 }?.let { ctx.getString(it) }
        ?: text

/***********************************************************************
 *                                URL                                  *
 ***********************************************************************/

val String.urlEncoded: String get() = URLEncoder.encode(this, "utf-8")
val String.urlDecoded: String get() = URLDecoder.decode(this, "utf-8")
fun String.decodeBase64(): ByteArray = Base64.decode(this, Base64.NO_WRAP)
fun String.decodeBase64AsString(): String = String(Base64.decode(this, Base64.NO_WRAP))
fun String.decodeBase64UrlSafeString(): String =
    String(Base64.decode(this, Base64.NO_WRAP or Base64.NO_PADDING or Base64.URL_SAFE))

fun ByteArray.encodeBase64(): String = Base64.encodeToString(this, Base64.NO_WRAP)
fun String.encodeBase64UrlSafeString(): String = Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP or Base64.URL_SAFE or Base64.NO_PADDING)
