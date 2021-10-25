package app.trikode.simampoeh.core.data.source.remote.utils

import app.trikode.simampoeh.core.data.source.remote.response.ResponsePackage
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan.PengajuanResponse
import app.trikode.simampoeh.domain.model.option.OptionMenu
import app.trikode.simampoeh.domain.model.tagihan.Tagihan
import app.trikode.simampoeh.domain.model.upload_berkas.BerkasResponse
import app.trikode.simampoeh.domain.model.user.KartuKeluarga
import app.trikode.simampoeh.domain.model.user.Penduduk
import app.trikode.simampoeh.domain.model.user.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException

object JsonResponseHelper {

    fun getResponse(responseObject: JsonObject): ResponsePackage? {
        var result: ResponsePackage? = null

        try {
            val responseResult = responseObject.get("response_result").asInt

            var responseMessage = ""
            if (responseObject.has("response_message")) responseMessage = responseObject.get("response_message").asString

            var responseData = JsonArray()
            if (responseObject.has("response_data")) {
                val checkAsArray = responseObject.get("response_data").isJsonArray

                responseData = if (checkAsArray) {
                    responseObject.get("response_data").asJsonArray
                } else {
                    val jsonObject = responseObject.get("response_data").asJsonObject
                    val jsonArray = JsonArray()
                    jsonArray.add(jsonObject)

                    jsonArray
                }
            }

            result = ResponsePackage(responseResult, responseMessage, responseData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun optionMenuResponseToDomain(response: JsonArray): ArrayList<OptionMenu> {
        val result = ArrayList<OptionMenu>()

        try {
            if (response.size() > 0) {
                for (i in 0 until response.size()) {
                    val data = response[i].asJsonObject
                    val id = data.get("id").asString
                    val opsi = data.get("nama").asString
                    val option = OptionMenu(id, opsi)

                    result.add(option)
                }
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun tagihanResponseToDomain(response: JsonArray): ArrayList<Tagihan> {
        val result = ArrayList<Tagihan>()

        try {
            if (response.size() > 0) {
                for (i in 0 until response.size()) {
                    val data = response[i].asJsonObject

                    val id = data.get("id").asString
                    val uid = data.get("uid").asString
                    val noSts = data.get("no_sts").asString
                    val jatuhTempo = data.get("jatuh_tempo").asString
                    val namaJenis = data.get("nama_jenis").asString

                    val tagihan = Tagihan(
                        id,
                        uid,
                        noSts,
                        jatuhTempo,
                        namaJenis
                    )

                    result.add(tagihan)
                }
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun getUploadBerkasUrlString(response: JsonArray): String {
        var result = ""

        try {
            if (response.size() > 0) {
                val data = response[0].asJsonObject
                result = data.get("url").asString
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun berkasToDomainMapper(response: JsonArray): ArrayList<BerkasResponse> {
        val result = ArrayList<BerkasResponse>()

        try {
            if (response.size() > 0) {
                for (i in 0 until response.size()) {
                    val data = response[i].asJsonObject
                    val id = data.get("id").asInt
                    val nama = data.get("nama").asString
                    val isRequired = data.get("is_required").asString
                    //val url = data.get("urlGambar").asString
                    val berkas = BerkasResponse(id, nama, isRequired, null)

                    result.add(berkas)
                }
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun pengajuanToDomainMapper(response: JsonArray): ArrayList<PengajuanResponse> {
        val result = ArrayList<PengajuanResponse>()

        try {
            if (response.size() > 0) {
                for (i in 0 until response.size()) {
                    val data = response[i].asJsonObject

                    val uid = data.get("uid").asString
                    val idStatus = data.get("id_status").asString
                    val jenis = data.get("jenis").asString
                    val kode = data.get("kode").asString
                    val waktuInput = data.get("waktu_input").asString
                    val namaPelayanan = data.get("nama_pelayanan").asString
                    val namaStatus = data.get("nama_status").asString

                    val berkas = PengajuanResponse(
                        uid,
                        kode,
                        jenis,
                        waktuInput,
                        namaStatus,
                        idStatus,
                        namaPelayanan
                    )

                    result.add(berkas)
                }
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun nikResponse(response: JsonArray): Penduduk? {
        var result: Penduduk? = null

        try {
            if (response.size() > 0) {
                val data = response[0].asJsonObject

                val nik = data.get("NIK").asString
                val nama = data.get("NAMA_LGKP").asString
                val tmptLahir = data.get("TMPT_LHR").asString
                val tglLahir = data.get("TGL_LHR").asString
                val jenkel = data.get("JENIS_KLMIN").asInt
                val agama = data.get("AGAMA").asString
                val provinsi = data.get("NO_PROP").asString
                val namaProvinsi = data.get("NAMA_PROP").asString
                val kabupaten = data.get("NO_KAB").asString
                val namaKabupaten = data.get("NAMA_KAB").asString
                val kecamatan = data.get("NO_KEC").asString
                val namaKecamatan = data.get("NAMA_KEC").asString
                val kelurahan = data.get("NO_KEL").asString
                val namaKelurahan = data.get("NAMA_KEL").asString
                val noKk = data.get("NO_KK").asString

                val dataKk = data.get("KARTU_KELUARGA").asJsonObject
                val namaKep = dataKk.get("NAMA_KEP").asString
                val alamat = dataKk.get("ALAMAT").asString

                result = Penduduk(
                    nik, nama, tmptLahir, tglLahir, jenkel, agama, provinsi, namaProvinsi, kabupaten,
                    namaKabupaten, kecamatan, namaKecamatan, kelurahan, namaKelurahan, noKk, namaKep, alamat)
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun kkResponse(response: JsonArray): KartuKeluarga? {
        var result: KartuKeluarga? = null

        try {
            if (response.size() > 0) {
                val data = response[0].asJsonObject

                val noKk = data.get("NO_KK").asString
                val alamat = data.get("ALAMAT").asString
                val anggotaKk = data.get("ANGGOTA_KK").asJsonArray

                val arrayAnggotaKk = arrayListOf<AnggotaKeluargaResponse>()
                for (i in 0 until anggotaKk.size()) {
                    val anggota = anggotaKk[i].asJsonObject

                    val nik = anggota.get("NIK").asString
                    val nama = anggota.get("NAMA_LGKP").asString

                    arrayAnggotaKk.add(AnggotaKeluargaResponse(nik, nama))
                }

                result = KartuKeluarga(
                    noKk,
                    alamat,
                    arrayAnggotaKk
                )
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }


    fun userResponseToDomain(response: JsonArray): User? {
        var result: User? = null

        try {
            if (response.size() > 0) {
                val data = response[0].asJsonObject

                val uid = data.get("uid").asString
                val nik = data.get("nik").asString
                val nama = data.get("nama").asString
                val email = data.get("email").asString

                val provinsi = data.get("nama_provinsi").asString
                val idProvinsi = data.get("id_provinsi").asString

                val kabupaten = data.get("nama_kabupaten").asString
                val idKabupaten = data.get("id_kabupaten").asString

                val kecamatan = data.get("nama_kecamatan").asString
                val idKecamatan = data.get("id_kecamatan").asString

                val kelurahan = data.get("nama_kelurahan").asString
                val idKelurahan = data.get("id_kelurahan").asString

                val alamat = data.get("alamat").asString

                result = User(uid, nik, nama, email, provinsi, idProvinsi, kabupaten, idKabupaten, kecamatan, idKecamatan, kelurahan, idKelurahan, alamat)
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return result
    }

}