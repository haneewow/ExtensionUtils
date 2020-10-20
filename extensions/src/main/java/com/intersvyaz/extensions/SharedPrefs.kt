package com.intersvyaz.extensions

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

fun SharedPreferences.getString(key: String) = this.getString(key, String.empty()) ?: String.empty()

fun <S> SharedPreferences.getObject(key: String, javaClass: Class<S>): S? {
    this.getString(key).also { json ->
        return if (key.isNotEmpty()) Gson().fromJson(json, javaClass)
        else null
    }
}

/**
 * Preference Extensions
 * @author Eugeniu Olog / ologe
 */
inline fun <reified T> SharedPreferences.observeKey(key: String, defaultValue: T): Flow<T> =
    channelFlow {
        offer(getItem(key, defaultValue))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
            if (key == k) offer(getItem(key, defaultValue))
        }

        registerOnSharedPreferenceChangeListener(listener)
        awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
    }
    .flowOn(Default)

inline fun <reified T> SharedPreferences.getItem(key: String, defaultValue: T): T =
    @Suppress("UNCHECKED_CAST")
    when (defaultValue) {
        is String -> getString(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        is Boolean -> getBoolean(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Set<*> -> getStringSet(key, defaultValue as Set<String>) as T
        is MutableSet<*> -> getStringSet(key, defaultValue as MutableSet<String>) as T
        else -> {
            Timber.w("Generic type not handled, type=${T::class.java.name}")
            null as T
        }
    }