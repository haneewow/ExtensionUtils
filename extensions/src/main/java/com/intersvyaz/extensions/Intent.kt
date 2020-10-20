package com.intersvyaz.extensions

import android.content.Intent
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