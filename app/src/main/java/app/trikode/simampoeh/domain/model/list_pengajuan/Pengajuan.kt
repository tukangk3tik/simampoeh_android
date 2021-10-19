package app.trikode.simampoeh.domain.model.list_pengajuan

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengajuan(
    var uid: String,
    var kode: String,
    var jenis: String,
    var waktuInput: String,
    var namaStatus: String,
    var idStatus: Int,
    var namaPelayanan: String
): Parcelable