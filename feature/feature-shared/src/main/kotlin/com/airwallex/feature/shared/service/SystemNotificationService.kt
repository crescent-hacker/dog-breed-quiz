package com.airwallex.feature.shared.service

interface SystemNotificationService {
    val areNotificationsEnabled: Boolean
    fun launchNotificationSettings()
}
