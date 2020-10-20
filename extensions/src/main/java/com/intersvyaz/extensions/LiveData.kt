package com.intersvyaz.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.intersvyaz.extensions.utils.ApiUtils.getJsonErrorMessage
import com.intersvyaz.extensions.utils.ApiUtils.getResponse
import com.intersvyaz.lk.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

private const val TAG = "LiveDataExtension"

fun <T> MutableLiveData<Resource<T>>.setRequestValue(apiQuery: (suspend () -> T)): Job {
    val data = this
    data.postValue(Resource.loading())
    return CoroutineScope(IO).launch {
        try {
            data.suspendValue(Resource.success(apiQuery()))
        } catch (e: HttpException) {
            val response = getResponse(e)
            val msg = getJsonErrorMessage(response)
            data.suspendValue(Resource.error(msg, code = e.code()))
        } catch (e: UnknownHostException) {
            data.suspendValue(Resource.error(NO_INTERNET))
        } catch (e: JsonSyntaxException) {
            Timber.tag(TAG).w("Incorrect JSON")
            data.suspendValue(Resource.error(String.empty()))
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Unknown crash")
            data.suspendValue(Resource.error(String.empty()))
        }
    }
}

const val NO_INTERNET = "-1"
const val DATA_BASE = "-2"
const val CRASH = "-3"

private fun <T> MutableLiveData<Resource<T>>.suspendValue(value: Resource<T>) {
    CoroutineScope(Main).launch { this@suspendValue.value = value }
}

fun <T> MutableLiveData<T>.getImmutable() = this as LiveData<T>
fun <T> LiveData<T>.getMutable() = this as MutableLiveData<T>
