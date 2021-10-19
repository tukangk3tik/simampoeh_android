package app.trikode.simampoeh.core.data.source.remote.response.kia

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class KiaResponse(

    @field:SerializedName("request")
    var request: String = "tambah_identitasanak",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("nik")
    var nik: String,

    @field:SerializedName("anak_noakta")
    var noAkta: String,

    @field:SerializedName("anak_ayah")
    var anakAyah: String?,

    @field:SerializedName("anak_ibu")
    var anakIbu: String?,

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
