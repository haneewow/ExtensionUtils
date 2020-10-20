package com.intersvyaz.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import timber.log.Timber

private const val TAG = "IntentExpansion"

@Suppress("UNCHECKED_CAST")
fun <S> Intent.getData(key: String): S? {
    return try {
        extras?.get(key) as S?
    } catch (e: Exception) {
        Timber.tag(TAG).w(e, "Error on getting data from intent")
        null
    }
}

fun Intent.handleNoActivity(appContext: Context) {
    Toast.makeText(
        appContext,
        appContext?.getString(R.string.intent_no_apps_to_handle),
        Toast.LENGTH_LONG
    ).show()
}

fun Intent.handleInvocationError(appContext: Context) {
    Toast.makeText(
        appContext,
        appContext?.getString(R.string.intent_invocation_error),
        Toast.LENGTH_LONG
    ).show()
}

fun Intent.invokeSafe(
    appContext: Context,
    onInvoke: (Intent) -> Unit,
    onSkip: ((Intent) -> Unit)? = null,
    onError: ((Intent) -> Unit)? = null
) {
    val packageManagerFlags = 0
    val intent = this
    try {
        val activitiesCount = appContext?.packageManager
            ?.queryIntentActivities(intent, packageManagerFlags)
            ?.size
            ?: 0

        if (activitiesCount > 0) onInvoke.invoke(intent)
        else {
            Timber.tag(TAG).w("Intent handler not found, intent=${intent}")
            onSkip?.invoke(intent) ?: handleNoActivity(appContext)
        }
    } catch (e: Exception) {
        Timber.tag(TAG).w(e, "Intent invocation error, intent=${intent}")
        onError?.invoke(intent) ?: handleInvocationError(appContext)
    }
}