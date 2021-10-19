package app.trikode.simampoeh.core.data.source.remote.response.surat_pindah

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import com.google.gson.annotations.SerializedName

data class SuratPindahResponse(

    @field:SerializedName("request")
    var request: String = "tambah_suratpindah",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("nik")
    var nik: String,

    @field:SerializedName("nama")
    var nama: String,

    @field:SerializedName("no_kk")
    var noKk: String,

    @field:SerializedName("id_provinsi_pindah")
    var idProvinsiPindah: String? = null,

    @field:SerializedName("id_kabupaten_pindah")
    var idKabupatenPindah: String? = null,

    @field:SerializedName("id_kecamatan_pindah")
    var idKecamatanPindah: String? = null,

    @field:SerializedName("id_kelurahan_pindah")
    var idKelurahanPindah: String? = null,

    @field:SerializedName("alamat_pindah")
    var alamatPindah: String? = null,

    @field:SerializedName("rt_tujuan")
    var rtTujuan: String? = null,

    @field:SerializedName("rw_tujuan")
    var rwTujuan: String? = null,

    @field:SerializedName("kodepos_tujuan")
    var kodeposTujuan: String? = null,

    @field:SerializedName("telepon")
    var telepon: String? = null,

    @field:SerializedName("alasan")
    var alasan: String? = null,

    @field:SerializedName("alasan_lainnya")
    var alasanLainnya: String? = null,

    @field:SerializedName("jenis_pindahan")
    var jenisPindahan: String? = null,

    @field:SerializedName("kk_bagi_tidak_pindah")
    var kkBagiTidakPindah: String? = null,

    @field:SerializedName("kk_bagi_pindah")
    var kkBagiPindah: String? = null,

    @field:SerializedName("id_anggota")
    var anggotaKeluargaPindah: ArrayList<AnggotaKeluargaResponse> = ArrayList(),

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
