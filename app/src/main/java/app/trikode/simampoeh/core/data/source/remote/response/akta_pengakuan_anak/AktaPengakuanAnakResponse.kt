package app.trikode.simampoeh.core.data.source.remote.response.akta_pengakuan_anak

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class AktaPengakuanAnakResponse(

    @field:SerializedName("request")
    var request: String = "tambah_aktaaku",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("nik")
    var nik: String,

    @field:SerializedName("anak_nama")
    var anakNama: String,

    @field:SerializedName("anak_jenkel")
    var anakJenkel: String?,

    @field:SerializedName("anak_kelahiran_ke")
    var anakKe: String?,

    @field:SerializedName("anak_nomor_akta")
    var nomorAkta: String?,

    @field:SerializedName("anak_tanggal_akta")
    var tglAkta: String?,

    @field:SerializedName("anak_dinas_akta")
    var dinasAkta: String?,

    @field:SerializedName("anak_id_jenkel")
    var anakIdJenkel: String?,

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
