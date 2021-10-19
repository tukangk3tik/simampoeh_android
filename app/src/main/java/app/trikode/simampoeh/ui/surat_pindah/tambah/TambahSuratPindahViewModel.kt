package app.trikode.simampoeh.ui.surat_pindah.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.core.data.source.remote.response.surat_pindah.SuratPindahResponse

class TambahSuratPindahViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }

    var dataPengajuan: SuratPindahResponse? = null

    var dataAnggota: ArrayList<AnggotaKeluargaResponse> = ArrayList()

    fun setAnggotaKeluarga() {
        dataPengajuan?.anggotaKeluargaPindah = dataAnggota
    }

}