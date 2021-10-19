package app.trikode.simampoeh.domain.repository

import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IAppRepository {

    suspend fun login(nik: String, password: String): ResponsePackage?

    suspend fun register(
        nik: String,
        nokk: String,
        email: String,
        noHp: String
    ): ResponsePackage?

    suspend fun resetPassword(
        nik: String,
        nokk: String
    ): ResponsePackage?

    suspend fun loadMaster(request: String, parent: String?): ResponsePackage?

    suspend fun loadWilayah(
        request: String?,
        provinsi: String?,
        kabupaten: String?,
        kecamatan: String?
    ): ResponsePackage?

    suspend fun getListBerkas(parent: String): ResponsePackage?

    suspend fun cekNik(nik: String): ResponsePackage?

    suspend fun getKk(noKk: String): ResponsePackage?

    suspend fun uploadAktaLahir(data: AktaLahir, berkas: Array<MultipartBody.Part>): ResponsePackage?

    suspend fun uploadBerkasPartial(
        imageToUpload: MultipartBody.Part,
        fileName: RequestBody,
        type: RequestBody,
        request: RequestBody
    ): ResponsePackage?

    suspend fun submitLayanan(request: String, data: String): ResponsePackage?

    suspend fun getHistoryPengajuan(): ResponsePackage?
}