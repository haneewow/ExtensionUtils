package com.intersvyaz.extensions

fun Boolean.toInt() = this.takeIf { it }?.let { 1 } ?: 0
