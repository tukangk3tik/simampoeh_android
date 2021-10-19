package app.trikode.simampoeh.ui.akta_lahir.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.akta_lahir.AktaLahirResponse

class TambahAktaLahirViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: AktaLahirResponse? = null

}
