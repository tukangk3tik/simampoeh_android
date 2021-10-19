package app.trikode.simampoeh.domain.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Penduduk(
    var nik: String,
    var nama: String,
    var tmptLahir: String,
    var tglLahir: String,
    var jenkel: Int,
    var agama: String?,
    var provinsi: String?,
    var namaProvinsi: String?,
    var kabupaten: String?,
    var namaKabupaten: String?,
    var kecamatan: String?,
    var namaKecamatan: String?,
    var kelurahan: String?,
    var namaKelurahan: String?,
    var noKk: String?,
    var namaKep: String?,
    var alamatKk: String?
): Parcelable
