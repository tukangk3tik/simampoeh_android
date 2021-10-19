package app.trikode.simampoeh.core.data.source.remote.response.akta_lahir

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class AktaLahirResponse(

    @field:SerializedName("request")
    var request: String = "tambah_aktalahir",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("anak_nama")
    var anakNama: String,

    @field:SerializedName("anak_tempat_lahir")
    var anakTempatLahir: String?,

    @field:SerializedName("anak_berat_bayi")
    var anakBeratBayi: String?,

    @field:SerializedName("anak_panjang_bayi")
    var anakPanjangBayi: String?,

    @field:SerializedName("anak_id_jenkel")
    var anakIdJenkel: String?,

    @field:SerializedName("anak_id_tempatlahir")
    var anakIdTempatKelahiran: String?,

    @field:SerializedName("anak_tanggal_lahir")
    var anakTglLahir: String,

    @field:SerializedName("anak_jam_lahir")
    var anakJamLahir: String?,

    @field:SerializedName("anak_kelahiran_ke")
    var kelahiranKe: String?,

    @field:SerializedName("anak_id_tolonglahir")
    var idPenolongKelahiran: String?,

    @field:SerializedName("anak_ayah")
    var namaAyah: String,

    @field:SerializedName("anak_ibu")
    var namaIbu: String,

    @field:SerializedName("anak_saksi1")
    var namaSaksi1: String,

    @field:SerializedName("anak_saksi2")
    var namaSaksi2: String,

    @field:SerializedName("anak_no_handphone")
    var noHp: String,

    @field:SerializedName("jenis_kelahiran")
    var jenisKelahiran: String,

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
