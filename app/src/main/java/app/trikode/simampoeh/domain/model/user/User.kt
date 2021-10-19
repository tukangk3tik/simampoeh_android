package app.trikode.simampoeh.domain.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String,
    val nik: String,
    val nama: String,
    val email: String,
    val provinsi: String,
    val idProvinsi: String,
    val kabupaten: String,
    val idKabupaten: String,
    val kecamatan: String,
    val idKecamatan: String,
    val kelurahan: String,
    val idKelurahan: String,
    val alamat: String,
): Parcelable
