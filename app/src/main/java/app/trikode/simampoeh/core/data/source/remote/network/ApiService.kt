package app.trikode.simampoeh.core.data.source.remote.network

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart


interface ApiService {

    //FORGOT PASSWORD
    @Headers("Accept: application/json")
    @POST("Member")
    @FormUrlEncoded
    suspend fun login(
        @Field("nik") nik: String,
        @Field("password") password: String,
        @Field("request") request: String = "login"
    ) : Response<JsonObject>

    //REGISTER
    @Headers("Accept: application/json")
    @POST("Member")
    @FormUrlEncoded
    suspend fun register(
        @Field("nik") nik: String,
        @Field("nokk") kk: String,
        @Field("email") email: String,
        @Field("no_handphone") noHp: String,
        @Field("request") request: String = "register"
    ) : Response<JsonObject>

    //LUPA PASSWORD
    @Headers("Accept: application/json")
    @POST("Member")
    @FormUrlEncoded
    suspend fun lupaPassword(
        @Field("nik") nik: String,
        @Field("nokk") kk: String,
        @Field("request") request: String = "lupa_password"
    ) : Response<JsonObject>

    //MASTER
    @Headers("Accept: application/json")
    @POST("master")
    @FormUrlEncoded
    suspend fun loadMaster(
        @Field("request") request: String,
        @Field("parent") parent: String?
    ) : Response<JsonObject>

    //WILAYAH
    @Headers("Accept: application/json")
    @POST("master")
    @FormUrlEncoded
    suspend fun loadWilayah(
        @Field("request") request: String?,
        @Field("id_provinsi") idProvinsi: String?,
        @Field("id_kabupaten") idKabupaten: String?,
        @Field("id_kecamatan") idKecamatan: String?
    ) : Response<JsonObject>

    //BERKAS
    @Headers("Accept: application/json")
    @POST("Master")
    @FormUrlEncoded
    suspend fun getListBerkas(
        @Field("parent") parent: String,
        @Field("request") request: String = "pelayanan_jenis_syarat"
    ) : Response<JsonObject>

    //REGISTER
    @POST("Pelaporan2")
    @Multipart
    suspend fun uploadBerkasPartial(
        @Part file: MultipartBody.Part,
        @Part("file_name") fileName: RequestBody,
        @Part("type") type: RequestBody,
        @Part("request") request: RequestBody
    ) : Response<JsonObject>

    //CEK NIK
    @Headers("Accept: application/json")
    @POST("Master")
    @FormUrlEncoded
    suspend fun cekNik(
        @Field("request") request: String = "nik",
        @Field("nik") nik: String
    ) : Response<JsonObject>

    //CEK NIK
    @Headers("Accept: application/json")
    @POST("Master")
    @FormUrlEncoded
    suspend fun getKk(
        @Field("request") request: String = "kk",
        @Field("kk") noKk: String
    ) : Response<JsonObject>

    //REGISTER
    @POST("upload_berkas_lahir.php")
    @Multipart
    suspend fun submitAktaLahir(
        @Part request: String = "tambah_akta_lahir",
        @Part data: String,
        @Part berkas: Array<MultipartBody.Part>
    ) : Response<JsonObject>

    //MASTER
    @Headers("Accept: application/json")
    @POST("Pelaporan2")
    @FormUrlEncoded
    suspend fun submitLayanan(
        @Field("request") request: String,
        @Field("data") data: String
    ) : Response<JsonObject>


    //BERKAS
    @Headers("Accept: application/json")
    @POST("Pelaporan2")
    @FormUrlEncoded
    suspend fun getHistoryPengajuan(
        @Field("request") request: String = "history_saya"
    ) : Response<JsonObject>

    //GET TAGIHAN
    @Headers("Accept: application/json")
    @POST("Pelaporan2")
    @FormUrlEncoded
    suspend fun getTagihan(
        @Field("request") request: String = "biaya",
    ) : Response<JsonObject>

}