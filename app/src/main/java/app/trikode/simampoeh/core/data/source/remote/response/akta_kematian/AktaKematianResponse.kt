package app.trikode.simampoeh.core.data.source.remote.response.akta_kematian

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class AktaKematianResponse(

    @field:SerializedName("request")
    var request: String = "tambah_aktamati",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("hubungan")
    var hubungan: String,

    @field:SerializedName("nama")
    var nama: String?,

    @field:SerializedName("id_jenkel")
    var idJenkel: String?,

    @field:SerializedName("tempat_lahir")
    var tempatLahir: String?,

    @field:SerializedName("tanggal_lahir")
    var tanggalLahir: String?,

    @field:SerializedName("id_agama")
    var idAgama: String?,

    @field:SerializedName("id_kewarganegaraan")
    var idKewarganegaraan: String,

    @field:SerializedName("alamat_mati")
    var alamatMati: String? = null,

    @field:SerializedName("rt")
    var rt: String? = null,

    @field:SerializedName("rw")
    var rw: String? = null,

    @field:SerializedName("id_provinsi_mati")
    var idProvinsiMati: String? = null,

    @field:SerializedName("id_kabupaten_mati")
    var idKabupatenMati: String? = null,

    @field:SerializedName("id_kecamatan_mati")
    var idKecamatanMati: String? = null,

    @field:SerializedName("id_kelurahan_mati")
    var idKelurahanMati: String? = null,

    @field:SerializedName("telepon")
    var telepon: String? = null,

    @field:SerializedName("no_handphone")
    var noHandphone: String? = null,

    @field:SerializedName("tanggal_meninggal")
    var tanggalMeninggal: String? = null,

    @field:SerializedName("jam_meninggal")
    var jamMeninggal: String? = null,

    @field:SerializedName("tempat_meninggal")
    var tempatMeninggal: String? = null,

    @field:SerializedName("penyebab_meninggal")
    var penyebabMeninggal: String? = null,

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
