package app.trikode.simampoeh.core.data.source.remote.response.ktp

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class KtpResponse(

    @field:SerializedName("request")
    var request: String = "tambah_ktp",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("nik")
    var nik: String,

    @field:SerializedName("alasan")
    var alasan: String,

    @field:SerializedName("jenkel")
    var jenkel: String?,

    //DELIVERY SERVICE AREA
    @field:SerializedName("kirim")
    override var kirim: String? = null,

    @field:SerializedName("id_provinsi")    
    override var idProvinsi: String? = null,

    @field:SerializedName("id_kabupaten")   
    override var idKabupaten: String? = null,

    @field:SerializedName("id_kecamatan")  
    override var idKecamatan: String? = null,

    @field:SerializedName("id_kelurahan")
    override var idKelurahan: String? = null,

    @field:SerializedName("alamat")
    override var alamat: String? = null,

    @field:SerializedName("kodepos")
    override var kodePos: String? = null,

    ): ITujuanKirim
