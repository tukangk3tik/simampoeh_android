package app.trikode.simampoeh.utils.click_listener.pengajuan

import android.view.View
import app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan.PengajuanResponse

interface PengajuanListClickListener {

    fun onItemClicked(view: View, pengajuanResponse: PengajuanResponse)

}