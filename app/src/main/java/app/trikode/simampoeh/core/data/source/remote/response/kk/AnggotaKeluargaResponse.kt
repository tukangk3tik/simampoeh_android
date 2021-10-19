package app.trikode.simampoeh.core.data.source.remote.response.kk

import com.google.gson.annotations.SerializedName


data class AnggotaKeluargaResponse(
    @field:SerializedName("nik")
    var nik: String,

    @field:SerializedName("nama")
    var nama: String,

    @field:SerializedName("shdk")
    var idShdk: String? = null,

    @field:SerializedName("nama_shdk")
    var namaShdk: String? = null,
)
