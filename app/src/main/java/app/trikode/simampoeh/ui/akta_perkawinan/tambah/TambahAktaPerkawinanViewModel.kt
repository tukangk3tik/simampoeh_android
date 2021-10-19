package app.trikode.simampoeh.ui.akta_perkawinan.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.akta_perkawinan.AktaPerkawinanResponse

class TambahAktaPerkawinanViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: AktaPerkawinanResponse? = null
}