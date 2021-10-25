package app.trikode.simampoeh.domain.model.tagihan

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tagihan(
    var id: String,
    var uid: String,
    var noSts: String,
    var jatuhTempo: String,
    var namaJenis: String
) : Parcelable