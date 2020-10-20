package com.intersvyaz.extensions.utils

import com.intersvyaz.extensions.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber


private const val TAG = "ApiUtils"

const val HEADER_PAGINATION = "X-Pagination-Page-Count"

object ApiUtils {

    fun getResponse(e: HttpException) = e.response()?.errorBody()?.string()

    fun getResponseJsonValue(response: String?, key: String) = try {
        JSONArray(response).getJSONObject(0).getString(key)
    } catch (e: JSONException) {
        try {
            JSONObject(response).getString(key)
        } catch (e: Exception) {
            null
        }
    } catch (e: Exception) {
        null
    }

    fun getJsonErrorMessage(
        response: String?,
        defaultMsg: String = R.string.default_error_unknown.toString(),
        log: (e: Exception) -> Unit = { Timber.tag(TAG).e(it, "json parse exception") }
    ): String {
        var msg: String? = null
        try {
            val jObjError = JSONArray(response)
            msg = jObjError.getJSONObject(0).getString("message")
        } catch (e1: JSONException) {
            try {
                msg = JSONObject(response).getString("message")
            } catch (e: Exception) {
                log(e)
            }
        } catch (e1: Exception) {
            log(e1)
        }
        return msg ?: defaultMsg
    }
}