package com.intersvyaz.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String, language: String = "ru"): String = SimpleDateFormat(pattern, Locale(language)).format(this)

val Date.stringDate get() = SimpleDateFormat("dd.MM.yyyy").format(this)!!

val Date.stringDateISO8601 get() = SimpleDateFormat("yyyy-MM-dd").format(this)!!

val Date.stringTime get() = SimpleDateFormat("kk:mm").format(this)!!

@SuppressLint("SimpleDateFormat")
fun getMonth(inputPattern: String, date: String): String = try {
    SimpleDateFormat(inputPattern).parse(date).run {
        SimpleDateFormat("LLLL", Locale("ru")).format(time)
    }
} catch (e: Exception) { String.empty() }
