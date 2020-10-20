package com.intersvyaz.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


fun Activity.showToast(@StringRes stringResId: Int) = showToast(getString(stringResId))

fun Activity.showToast(message: String) {
    CoroutineScope(Main).launch {
        Toast.makeText(this@showToast, message, Toast.LENGTH_LONG).show()
    }
}
