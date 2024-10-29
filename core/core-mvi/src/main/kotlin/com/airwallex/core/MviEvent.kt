package com.airwallex.core

/**
 * Represents the "Intent" in MVI architecture, using keyword "Event" here trying to avoid the confusion with Android
 * Intent
 * 1. could be dispatched from Compose via ViewModel
 * 2. could be dispatched from an side effect via ViewModel
 */
interface MviEvent
