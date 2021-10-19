package app.trikode.simampoeh.utils.click_listener

import android.view.View
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse

interface AnggotaKeluargaCheckListener {

    fun onItemChecked(view: View, anggotaKeluarga: AnggotaKeluargaResponse, isChecked: Boolean)

}