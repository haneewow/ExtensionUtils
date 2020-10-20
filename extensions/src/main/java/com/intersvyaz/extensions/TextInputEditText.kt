package com.intersvyaz.extensions

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.removeSpacesAfterTextChanged() {
    doAfterTextChanged {
        it?.run {
            val trimmedText = this.toString().replace(" ", "")
            if (this.toString() != trimmedText)
                setText(trimmedText)
        }
    }
}
