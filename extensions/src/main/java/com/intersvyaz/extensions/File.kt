package com.intersvyaz.extensions

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

val File.multipart: MultipartBody.Part
    get() = RequestBody.create(MediaType.parse("*/*"), this).run {
        MultipartBody.Part.createFormData("file", name, this)
    }
