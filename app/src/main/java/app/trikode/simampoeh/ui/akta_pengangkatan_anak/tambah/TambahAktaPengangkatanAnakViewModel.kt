package app.trikode.simampoeh.ui.akta_pengangkatan_anak.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.akta_pengangkatan_anak.AktaPengangkatanAnakResponse

class TambahAktaPengangkatanAnakViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: AktaPengangkatanAnakResponse? = null

}