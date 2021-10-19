package app.trikode.simampoeh.ui.akta_pengesahan_anak.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.anak_pengesahan_anak.AktaPengesahanAnakResponse

class TambahAktaPengesahanAnakViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: AktaPengesahanAnakResponse? = null

}