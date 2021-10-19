package app.trikode.simampoeh.utils.click_listener.menu

import android.view.View
import app.trikode.simampoeh.domain.model.informasi.Informasi

interface InformasiClickListener {

    fun onInformasiItemClicked(view: View, informasi: Informasi)

}