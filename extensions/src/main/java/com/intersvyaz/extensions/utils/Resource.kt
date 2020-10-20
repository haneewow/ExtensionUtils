/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intersvyaz.extensions.utils

import com.intersvyaz.extensions.utils.Status.ERROR
import com.intersvyaz.extensions.utils.Status.LOADING
import com.intersvyaz.extensions.utils.Status.SUCCESS

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null, val code: Int? = null) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): Resource<T>? =
        if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            this
        }

    fun handleState(
        onLoading: (() -> Unit)? = null,
        onSuccess: ((T?) -> Unit)? = null,
        onError: ((String?, T?, Int?) -> Unit)? = null
    ) {
        when(status) {
            LOADING -> onLoading?.invoke()
            SUCCESS -> onSuccess?.invoke(data)
            ERROR -> onError?.invoke(message, data, code)
        }
    }

    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(SUCCESS, data)

        fun <T> error(
            msg: String,
            data: T? = null,
            code: Int? = null
        ): Resource<T> = Resource(ERROR, data, msg, code)

        fun <T> loading(data: T? = null): Resource<T> = Resource(LOADING, data)
    }
}
