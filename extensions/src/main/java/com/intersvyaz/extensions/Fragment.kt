package com.intersvyaz.extensions

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.intersvyaz.extensions.utils.UiUtils.hideKeyboard
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

fun Fragment.showMessage(message: String) = view?.let {
    Snackbar
        .make(it, message, Snackbar.LENGTH_SHORT)
        .show()
}

fun Fragment.showMessage(id: Int) = view?.context?.let {
    showMessage(it.getString(id))
}

fun Fragment.showError(message: String, action: () -> Unit) = view?.let {
    Snackbar
        .make(it, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(getString(R.string.default_update)) { action() }
        .show()
}

fun Fragment.showCenterToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).apply {
        this.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        show()
    }
}

fun Fragment.showError(message: String, actionText: String, action: () -> Unit) = view?.let {
    Snackbar
        .make(it, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText) { action.invoke() }
        .show()
}

fun Fragment.showApiMsgToast(message: String?) {
    Toast.makeText(view?.context, message?.takeIf { it.isNotEmpty() }
        ?: getText(R.string.default_error_unknown).toString(), Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) = lifecycleScope.launch(Main) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun Fragment.showAlert(message: String? = null,
                       positiveAction: Pair<String?, () -> Unit?> = Pair(getString(R.string.button_ok), {}),
                       negativeAction: Pair<String?, () -> Unit?>? = null,
                       neutralAction: Pair<String, () -> Unit>? = null,
                       cancelable: Boolean? = true) =
    showAlert(null, message, positiveAction, negativeAction, neutralAction, cancelable)

fun Fragment.showAlert(title: String? = null,
                       message: String? = null,
                       positiveAction: Pair<String?, () -> Unit?> = Pair(getString(R.string.button_ok), {}),
                       negativeAction: Pair<String?, () -> Unit?>? = null,
                       neutralAction: Pair<String, () -> Unit>? = null,
                       cancelable: Boolean? = true) {
    view?.run {
        @Suppress("NAME_SHADOWING")
        context?.let { context ->
            val message =
                if (message !== null && message.isNotEmpty()) message
                else context.getString(R.string.default_error_unknown)
            val cancelable = cancelable ?: true
            AlertDialog.Builder(context).apply {
                positiveAction.run { setPositiveButton(first) { _, _ -> second() } }
                negativeAction?.run { setNegativeButton(first) { _, _ -> second() } }
                neutralAction?.run { setNeutralButton(first) { _, _ -> second() } }
                setTitle(title)
                setMessage(message)
                setCancelable(cancelable)
                show()
            }
        }
    }
}

fun Fragment.hideKeyboard() = view?.let {
   hideKeyboard(requireActivity(), it)
}

fun Fragment.clearFocus() = view?.run {
    clearFocus()
}