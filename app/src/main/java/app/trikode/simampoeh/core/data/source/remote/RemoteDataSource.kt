package app.trikode.simampoeh.core.data.source.remote

import app.trikode.simampoeh.core.data.source.remote.network.ApiService
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(apiService)
            }
    }

    suspend fun login(nik: String, password: String): ResponsePackage? {
        val response = apiService.login(nik, password)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun register(
        nik: String,
        nokk: String,
        email: String,
        noHp: String
    ): ResponsePackage? {
        val response = apiService.register(nik, nokk, email, noHp)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun lupaPassword(
        nik: String,
        nokk: String
    ): ResponsePackage? {
        val response = apiService.lupaPassword(nik, nokk)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun loadMaster(
        request: String,
        parent: String?,
    ): ResponsePackage? {
        val response = apiService.loadMaster(request, parent)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun loadWilayah(
        request: String?,
        provinsi: String?,
        kabupaten: String?,
        kecamatan: String?
    ): ResponsePackage? {
        val response = apiService.loadWilayah(request, provinsi, kabupaten, kecamatan)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }


    suspend fun getListBerkas(
        parent: String,
    ): ResponsePackage? {
        val response = apiService.getListBerkas(parent)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun uploadBerkasPartial(
        file: MultipartBody.Part,
        fileName: RequestBody,
        type: RequestBody,
        request: RequestBody
    ): ResponsePackage? {
        val response = apiService.uploadBerkasPartial(file, fileName, type, request)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun cekNik(
        nik: String,
    ): ResponsePackage? {
        val response = apiService.cekNik(nik = nik)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }


    suspend fun getKk(noKk: String): ResponsePackage? {
        val response = apiService.getKk(noKk = noKk)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    //SUBMIT AKTA LAHIR
    suspend fun submitAktaLahir(
        data: AktaLahir,
        berkas: Array<MultipartBody.Part>
    ): ResponsePackage? {
        val jsonString = Gson().toJson(data)
        val response = apiService.submitAktaLahir(data = jsonString, berkas = berkas)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun submitLayanan(
        request: String,
        data: String,
    ): ResponsePackage? {
        val response = apiService.submitLayanan(request, data)
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun getHistoryPengajuan(): ResponsePackage? {
        val response = apiService.getHistoryPengajuan()
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }

    suspend fun getTagihan(): ResponsePackage? {
        val response = apiService.getTagihan()
        return if (response.isSuccessful) {
            val body = response.body()
            var responsePackage: ResponsePackage? = null
            if (body != null) {
                responsePackage = JsonResponseHelper.getResponse(body)
            }
            responsePackage
        } else {
            null
        }
    }
}