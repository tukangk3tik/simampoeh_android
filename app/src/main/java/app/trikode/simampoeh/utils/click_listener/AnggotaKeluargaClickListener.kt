package app.trikode.simampoeh.utils.click_listener

import android.view.View
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse

interface AnggotaKeluargaClickListener {

    fun onItemClicked(view: View, anggotaKeluarga: AnggotaKeluargaResponse)

}