package app.trikode.simampoeh.domain.model.cek_layanan

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CekLayananResponse (
    var id: Int,
    var status: String
): Parcelable