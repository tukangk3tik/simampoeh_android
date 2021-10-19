package app.trikode.simampoeh.core.data.source.remote.response.kk

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class KartuKeluargaResponse(

    @field:SerializedName("request")
    var request: String = "tambah_kartukeluarga",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("nik")    
    var nik: String,

    @field:SerializedName("nama")    
    var nama: String,

    @field:SerializedName("shdk")   
    var idShdk: String?,

    @field:SerializedName("id_kecamatan_pemohon")
    var idKecamatanPemohon: String?,

    @field:SerializedName("id_kelurahan_pemohon")
    var idKelurahanPemohon: String?,

    @field:SerializedName("alamat_pemohon") 
    var alamatPemohon: String?,

    @field:SerializedName("alasan") 
    var alasanPermohonan: String?,

    @field:SerializedName("no_kk_lama") 
    var noKkLama: String,

    @field:SerializedName("kirim")    
    override var kirim: String? = null,

    @field:SerializedName("alamat")
    override var alamat: String? = null,

    @field:SerializedName("kodepos")
    override var kodePos: String? = null,

    @field:SerializedName("anggota")
    var anggotaKeluarga: ArrayList<AnggotaKeluargaResponse>? = null,

    @field:SerializedName("id_provinsi")    
    override var idProvinsi: String? = null,

    @field:SerializedName("id_kabupaten")   
    override var idKabupaten: String? = null,

    @field:SerializedName("id_kecamatan")  
    override var idKecamatan: String? = null,

    @field:SerializedName("id_kelurahan")
    override var idKelurahan: String? = null

): ITujuanKirim
