package com.airwallex.core.util

import android.os.SystemClock

const val TIMESTAMP_UNINITIALIZED = -1L

fun isOverTimeout(pastTimestamp: Long, timeoutDuration: Long, useTimeSinceBoot: Boolean): Boolean {
    val now = if (useTimeSinceBoot) SystemClock.elapsedRealtime() else System.currentTimeMillis()
    return pastTimestamp == TIMESTAMP_UNINITIALIZED || now - pastTimestamp > timeoutDuration
}
