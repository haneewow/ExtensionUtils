package com.intersvyaz.extensions

import android.text.SpannableString
import android.text.style.UnderlineSpan
import java.text.SimpleDateFormat
import java.util.*

fun String.Companion.empty(): String = ""

fun String.toSpannableString() = SpannableString(this).apply {
    setSpan(UnderlineSpan(), 0, length, 0)
}

fun String.parseToDate(pattern: String, language: String = "ru"): Date = SimpleDateFormat(pattern, Locale(language)).parse(this)

fun String.apiStringToDate() = SimpleDateFormat("yyyy-MM-dd").parse(this)!!

fun String.apiTimeStampToDate() = SimpleDateFormat("yyyy-MM-dd kk:mm").parse(this)!!

fun String.toDate() = SimpleDateFormat("dd.MM.yyyy").parse(this)!!

fun String.toBool() = this == "1"

fun String.isNullLike() = isNullOrEmpty() || equals("null")

fun String.toNullable() = if (isNullLike()) null else this

fun String.isNumber() = matches("-?\\d+(\\.\\d+)?".toRegex())

fun String.lastSubstringWithSeparator(separator: String) = split(separator).last()
