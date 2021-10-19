package app.trikode.simampoeh.domain.usecase

import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import app.trikode.simampoeh.domain.repository.IAppRepository
import okhttp3.MultipartBody

class AppInteractor(private val appRepository: IAppRepository): AppUseCase {

    override suspend fun login(nik: String, password: String): ResponsePackage? =
        appRepository.login(nik, password)

    override suspend fun register(
        nik: String,
        nokk: String,
        email: String,
        noHp: String
    ): ResponsePackage? {
        return appRepository.register(nik, nokk, email, noHp)
    }

    override suspend fun loadMaster(request: String, parent: String?): ResponsePackage? {
        return appRepository.loadMaster(request, parent)
    }

    override suspend fun uploadAktaLahir(
        data: AktaLahir,
        berkas: Array<MultipartBody.Part>
    ): ResponsePackage? {
        return appRepository.uploadAktaLahir(data, berkas)
    }

}