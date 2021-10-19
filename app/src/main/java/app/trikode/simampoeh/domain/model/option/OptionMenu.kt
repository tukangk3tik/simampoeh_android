package app.trikode.simampoeh.domain.model.option

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionMenu(
    val id: String,
    val namaOpsi: String
): Parcelable