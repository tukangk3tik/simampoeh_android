package app.trikode.simampoeh.core.data.source.remote.network

import android.content.Context
import android.util.Log.d
import app.trikode.simampoeh.core.data.source.remote.response.ApiResponseResult
import app.trikode.simampoeh.utils.session.SessionHelper
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.Exception

class ApiResponseInterceptor(val context: Context): Interceptor {

    val mGson = Gson()
    val mJson = "application/json; charset=utf-8".toMediaTypeOrNull()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val body = response.body

        var apiResponse = ApiResponseResult(JsonObject())
        var responsePackage = ""
        try {
            apiResponse = mGson.fromJson(body?.string(), ApiResponseResult::class.java)
            responsePackage = apiResponse.responsePackage.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        body?.close()

        d("RESULT_INTERCEPTOR", apiResponse.toString())
        //get token, check if not null then update
        val newToken = apiResponse.token
        if (newToken != null) SessionHelper.updateToken(newToken, context)
        //d("TOKEN_NEW", SessionHelper.getToken(context))

        val newResponse = response
            .newBuilder()
            .body(responsePackage.toResponseBody(mJson))

        return newResponse.build()
    }

}