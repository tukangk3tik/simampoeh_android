package app.trikode.simampoeh.core.data.source

import app.trikode.simampoeh.core.data.source.remote.RemoteDataSource
import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.core.utils.AppExecutors
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import app.trikode.simampoeh.domain.repository.IAppRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AppRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): IAppRepository {

    companion object {
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            appExecutors: AppExecutors
        ): AppRepository = instance ?: synchronized(this) {
            instance ?: AppRepository(remoteData, appExecutors)
        }
    }

    override suspend fun login(nik: String, password: String): ResponsePackage? {
        return remoteDataSource.login(nik, password)
    }

    override suspend fun register(
        nik: String,
        nokk: String,
        email: String,
        noHp: String
    ): ResponsePackage? {
        return remoteDataSource.register(nik, nokk, email, noHp)
    }

    override suspend fun resetPassword(nik: String, nokk: String): ResponsePackage? {
        return remoteDataSource.lupaPassword(nik, nokk)
    }

    override suspend fun loadMaster(request: String, parent: String?): ResponsePackage? {
        return remoteDataSource.loadMaster(request, parent)
    }

    override suspend fun loadWilayah(
        request: String?,
        provinsi: String?,
        kabupaten: String?,
        kecamatan: String?,
    ): ResponsePackage? {
        return remoteDataSource.loadWilayah(request, provinsi, kabupaten, kecamatan)
    }

    override suspend fun getListBerkas(parent: String): ResponsePackage? {
        return remoteDataSource.getListBerkas(parent)
    }

    /**
     * Upload images to the server
     */
    override suspend fun uploadBerkasPartial(
        imageToUpload: MultipartBody.Part,
        fileName: RequestBody,
        type: RequestBody,
        request: RequestBody
    ): ResponsePackage? {
        return remoteDataSource.uploadBerkasPartial(imageToUpload, fileName, type, request)
    }

    override suspend fun submitLayanan(request: String, data: String): ResponsePackage? {
        return remoteDataSource.submitLayanan(request, data)
    }

    override suspend fun getHistoryPengajuan(): ResponsePackage? {
        return remoteDataSource.getHistoryPengajuan()
    }

    override suspend fun cekNik(nik: String): ResponsePackage? {
        return remoteDataSource.cekNik(nik)
    }

    override suspend fun getKk(noKk: String): ResponsePackage? {
        return remoteDataSource.getKk(noKk)
    }

    override suspend fun uploadAktaLahir(
        data: AktaLahir,
        berkas: Array<MultipartBody.Part>
    ): ResponsePackage? {
        return remoteDataSource.submitAktaLahir(data, berkas)
    }

}
