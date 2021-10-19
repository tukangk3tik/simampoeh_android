package app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.akta_pengakuan_anak.AktaPengakuanAnakResponse

class TambahAktaPengakuanAnakViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: AktaPengakuanAnakResponse? = null


}