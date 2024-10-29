package com.airwallex.dogquiz.main

import android.content.Context
import android.content.Intent
import com.airwallex.core.designsystem.util.longToast
import com.airwallex.core.designsystem.util.toast
import com.airwallex.core.util.AppLogger
import com.airwallex.core.util.GlobalEvent
import com.airwallex.core.util.resolve
import com.airwallex.feature.shared.data.model.dto.AppEvent
import javax.inject.Inject

class AppEventProcessor @Inject constructor(
    private val ctx: Context,
) : GlobalEvent.Processor<AppEvent> {

    /**
     * process app events
     *
     * TODO, use strategy pattern to create processor for each event type
     */
    override fun process(event: AppEvent): Unit = with(ctx) {
        when (event) {
            is AppEvent.RestartActivityEvent -> {
                AppLogger.debug("Received RestartActivityEvent: $event")
                startActivity(
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                )
            }

            is AppEvent.ToastEvent -> {
                if (event.isLong) {
                    longToast(text = event.text.resolve(this))
                } else {
                    toast(text = event.text.resolve(this))
                }
            }

            else -> Unit
        }
    }

    private val mainViewModel get() = (ctx as MainActivity).mainViewModel
}
