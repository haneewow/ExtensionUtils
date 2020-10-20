package com.intersvyaz.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun ImageView.setDrawable(@DrawableRes id: Int) =
    this.setImageDrawable(ContextCompat.getDrawable(context, id))
