package com.airwallex.feature.shared.service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.airwallex.core.util.AppLogger
import com.airwallex.core.util.nonFatalError
import javax.inject.Inject

class SystemNotificationServiceImpl @Inject constructor(
    private val ctx: Context
) : SystemNotificationService {

    private val notificationManagerCompat = NotificationManagerCompat.from(ctx)
    override val areNotificationsEnabled: Boolean
        get() = notificationManagerCompat.areNotificationsEnabled()

    override fun launchNotificationSettings() = with(ctx) {
        try {
            startActivity(
                Intent().apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                        }
                        else -> {
                            action = "android.settings.APP_NOTIFICATION_SETTINGS"
                            putExtra("app_package", packageName)
                            putExtra("app_uid", applicationInfo.uid)
                        }
                    }
                }
            )
        } catch (e: Exception) {
            AppLogger.nonFatalError(e)
        }
    }
}
