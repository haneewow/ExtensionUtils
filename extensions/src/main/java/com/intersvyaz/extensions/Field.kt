package com.intersvyaz.extensions

import java.lang.reflect.Field
import java.lang.reflect.Modifier

@Throws(Exception::class)
fun Field.setFinalStatic(value: Any?) {
    isAccessible = true
    Field::class.java.getDeclaredField("modifiers").apply {
        isAccessible = true
        setInt(this@setFinalStatic, this@setFinalStatic.modifiers and Modifier.FINAL.inv())
    }
    set(null, value)
}
