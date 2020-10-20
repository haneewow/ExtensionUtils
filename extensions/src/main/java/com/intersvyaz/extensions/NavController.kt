package com.intersvyaz.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import timber.log.Timber


fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    (currentDestination?.getAction(resId) ?: graph.getAction(resId)).also {
        // проверка на повторный вызов перехода из экрана-назначения в него же
        if (it != null && currentDestination?.id != it.destinationId) {
            try {
                navigate(resId, args, navOptions, navExtras)
            } catch (e: Exception) {
                Timber.d(e, "Error with navigate")
            }
        }
    }
}
