package com.intersvyaz.extensions

import com.google.gson.Gson
import com.google.gson.JsonParseException
import timber.log.Timber

inline fun <reified T> Gson.safetyFromJson(json: String): T? = try {
    fromJson<T>(json, T::class.java)
} catch (ex: JsonParseException) {
    Timber
        .w(ex, "Error on parse json. Input json: %s. Target class: %s",
            json, T::class.java.simpleName)
    null
}