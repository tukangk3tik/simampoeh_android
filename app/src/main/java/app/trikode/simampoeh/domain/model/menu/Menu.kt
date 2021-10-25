package app.trikode.simampoeh.domain.model.menu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
    var id : Int,
    var nameStrRes : Int,
    var imageIcon : Int,
    var route : String,
    var urlString: String?,
    var parent : Int? = null,
    var child: List<Menu>? = null
) : Parcelable

