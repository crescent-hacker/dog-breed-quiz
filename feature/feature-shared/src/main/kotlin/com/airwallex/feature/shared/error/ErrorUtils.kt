package com.airwallex.feature.shared.error

import android.content.Context
import com.airwallex.core.basic.model.AirwallexException
import com.airwallex.core.designsystem.util.wrap
import com.airwallex.core.util.TextHolder
import com.airwallex.feature.shared.R

fun <T> Result<T>.resolveErrorMessage(): TextHolder =
    if (this.isFailure) {
        this.exceptionOrNull()?.message?.wrap()
            ?: genericErrorMessage
    } else
        throw AirwallexException("Cannot resolve error message from a successful result.")

fun <T> Result<T>.resolveErrorMessage(ctx: Context): String? =
    if (this.isFailure) {
        this.exceptionOrNull()?.message
            ?: ctx.getString(R.string.com_airwallex_feature_shared_error_msg_generic)
    } else null

fun Throwable?.resolveErrorMessage(): TextHolder =
    this?.message?.wrap() ?: genericErrorMessage

val genericErrorMessage: TextHolder get() = R.string.com_airwallex_feature_shared_error_msg_generic.wrap()
val shortGenericErrorMessage: TextHolder get() = R.string.com_airwallex_feature_shared_error_state_generic_title.wrap()
val genericErrorTitle: TextHolder get() = R.string.com_airwallex_feature_shared_error_msg_generic_title.wrap()
val genericErrorCopy: TextHolder get() = R.string.com_airwallex_feature_shared_error_msg_generic_copy.wrap()


