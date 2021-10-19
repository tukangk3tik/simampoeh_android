package app.trikode.simampoeh.domain.usecase

import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import okhttp3.MultipartBody

interface AppUseCase {

    suspend fun login(nik: String, password: String): ResponsePackage?

    suspend fun register(nik: String, nokk: String, email: String, noHp: String): ResponsePackage?

    suspend fun loadMaster(request: String, parent: String?): ResponsePackage?

    suspend fun uploadAktaLahir(data: AktaLahir, berkas: Array<MultipartBody.Part>): ResponsePackage?

}