package app.trikode.simampoeh.core.data.source.remote.response

import com.google.gson.JsonArray

data class ResponsePackage(
    val responseResult: Int,
    val responseMessage: String,
    val responseData: JsonArray
)
