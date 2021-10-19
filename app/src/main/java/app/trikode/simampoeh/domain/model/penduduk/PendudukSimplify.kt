package app.trikode.simampoeh.domain.model.penduduk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendudukSimplify(
    var uid: String,
    var nik: String,
    var nama: String,
    var nokk: String,
    var jenisKelamin: String
): Parcelable