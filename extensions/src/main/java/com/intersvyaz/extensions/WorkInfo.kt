package com.intersvyaz.extensions

import androidx.work.WorkInfo

fun <T> WorkInfo.getData(key: String) = outputData
    .keyValueMap[key] as? T