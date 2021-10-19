package app.trikode.simampoeh.core.data.source.remote.network

import android.content.Context
import app.trikode.simampoeh.utils.session.SessionHelper
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        //custom function in object to get token
        SessionHelper.getToken(context).let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}