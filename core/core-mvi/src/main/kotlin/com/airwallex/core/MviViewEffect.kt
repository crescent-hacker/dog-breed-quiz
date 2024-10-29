package com.airwallex.core

/**
 * Represents UI side effect in MVI architecture, usually to trigger global or external UI change outside of
 * a compose function like Navigation, Toast, Snack Bar, etc.
 */
interface MviViewEffect

/**
 * Used this for the [MviViewEffect] generic type when you don't need to manage side effect in the Compose Screen
 */
object EmptyViewEffect : MviViewEffect

data class ToastEffect(val message: String): MviViewEffect

data class SnackBarEffect(
        val message: String,
        val isSuccess: Boolean = true,
        val delayMillis: Long = 0,
        val onDismiss: () -> Unit = {}
): MviViewEffect


