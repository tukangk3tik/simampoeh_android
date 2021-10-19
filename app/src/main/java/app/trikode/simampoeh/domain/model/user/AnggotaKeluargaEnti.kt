package app.trikode.simampoeh.domain.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnggotaKeluargaEnti(
    var nik: String,
    var nama: String,
    var idShdk: String,
    var namaShdk: String,
): Parcelable
