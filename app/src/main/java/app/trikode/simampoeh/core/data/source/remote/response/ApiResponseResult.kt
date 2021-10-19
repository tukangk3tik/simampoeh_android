package app.trikode.simampoeh.core.data.source.remote.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ApiResponseResult(
    @field:SerializedName("response_package")
    val responsePackage: JsonObject,

    @field:SerializedName("token")
    val token: String? = null
)


