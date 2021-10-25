package app.trikode.simampoeh.utils.click_listener.tagihan

import android.view.View
import app.trikode.simampoeh.domain.model.tagihan.Tagihan

interface TagihanListClickListener {

    fun onItemClicked(view: View, tagihan: Tagihan)

}