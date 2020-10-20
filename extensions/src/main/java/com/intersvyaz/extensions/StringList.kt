package com.intersvyaz.extensions

import java.util.*

fun List<String>.toCalendarList() : MutableList<Calendar>{
    val list = mutableListOf<Calendar>()
    for (item in this)
        list.add(Calendar.getInstance().apply { time = item.apiStringToDate() })
    return list
}
