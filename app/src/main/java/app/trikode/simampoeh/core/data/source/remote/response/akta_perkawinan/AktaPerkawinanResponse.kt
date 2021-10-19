package app.trikode.simampoeh.core.data.source.remote.response.akta_perkawinan

import app.trikode.simampoeh.core.data.source.remote.response.ITujuanKirim
import com.google.gson.annotations.SerializedName

data class AktaPerkawinanResponse(

    @field:SerializedName("request")
    var request: String = "tambah_aktakawin",

    @field:SerializedName("id_pelayanan")
    var idPelayanan: String,

    @field:SerializedName("jenis")  
    var idJenis: String,

    @field:SerializedName("id_agama")
    var idAgama: String,

    @field:SerializedName("tanggal_kawin")
    var tanggalKawin: String,

    @field:SerializedName("organisasi_penghayat")
    var organisasiPenghayat: String,

    @field:SerializedName("badan_peradilan")
    var badanPeradilan: String?,

    @field:SerializedName("nomor_pengadilan")
    var nomorPengadilan: String?,

    @field:SerializedName("tanggal_pengadilan")
    var tanggalPengadilan: String?,

    @field:SerializedName("nama_pemuka")
    var namaPemukaAgama: String?,

    @field:SerializedName("izin_perwakilan")
    var izinPerwakilanWna: String?,

    @field:SerializedName("jumlah_anak")
    var jumlahAnak: String?,

    @field:SerializedName("nik_suami")
    var nikSuami: String? = null,

    @field:SerializedName("nik_istri")
    var nikIstri: String? = null,

    @field:SerializedName("nama_suami")
    var namaSuami: String? = null,

    @field:SerializedName("nama_istri")
    var namaIstri: String? = null,

    @field:SerializedName("nik_suami_ayah")
    var nikSuamiAyah: String? = null,

    @field:SerializedName("nama_suami_ayah")
    var namaSuamiAyah: String? = null,

    @field:SerializedName("nik_suami_ibu")
    var nikSuamiIbu: String? = null,

    @field:SerializedName("nama_suami_ibu")
    var namaSuamiIbu: String? = null,

    @field:SerializedName("nik_istri_ayah")
    var nikIstriAyah: String? = null,

    @field:SerializedName("nama_istri_ayah")
    var namaIstriAyah: String? = null,

    @field:SerializedName("nik_istri_ibu")
    var nikIstriIbu: String? = null,

    @field:SerializedName("nama_istri_ibu")
    var namaIstriIbu: String? = null,

    @field:SerializedName("nik_saksi_1")
    var nikSaksi1: String? = null,

    @field:SerializedName("nama_saksi_1")
    var namaSaksi1: String? = null,

    @field:SerializedName("nik_saksi_2")
    var nikSaksi2: String? = null,

    @field:SerializedName("nama_saksi_2")
    var namaSaksi2: String? = null,

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
