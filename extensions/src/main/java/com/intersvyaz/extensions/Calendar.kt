package com.intersvyaz.extensions

import java.text.SimpleDateFormat
import java.util.*

val Calendar.apiStringDate get() = SimpleDateFormat("yyyy-MM-dd").format(this.time)!!

val Calendar.stringDate get() = SimpleDateFormat("dd.MM.yyyy").format(this.time)!!

val Calendar.stringTime get() = SimpleDateFormat("hh:HH").format(this.time)!!

fun Calendar.format(
    pattern: String,
    language: String = "ru"
): String = SimpleDateFormat(pattern, Locale(language)).format(this.time)
