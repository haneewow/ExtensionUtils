package com.intersvyaz.extensions

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Task<T>.await() : Task<T> =
    suspendCoroutine { continuation ->
        addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
