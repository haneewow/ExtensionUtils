package com.intersvyaz.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity


tailrec fun Context?.activity(): AppCompatActivity? = if (this is AppCompatActivity) this
else (this as? ContextWrapper)?.baseContext?.activity()
