package app.trikode.simampoeh.domain.model.upload_berkas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Berkas(
    var id: String,
    var namaBerkas: String,
    var wajib: String,
    var statusWajib: String
): Parcelable


@Parcelize
data class BerkasResponse(
    var id: Int,
    var nama: String,
    var isRequired: String,
    var urlGambar: String?
): Parcelable
