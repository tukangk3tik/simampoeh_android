package app.trikode.simampoeh.ui.kk.tambah

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.core.data.source.remote.response.kk.KartuKeluargaResponse

class TambahKkViewModel: ViewModel() {

    fun getLayananId(): String = _jenisLayananId

    private var _jenisLayananId = "0"

    fun setJenisLayananId(id: String) {
        _jenisLayananId = id
    }


    var dataPemohon: KartuKeluargaResponse? = null

    private val dataAnggotaKeluarga: ArrayList<AnggotaKeluargaResponse> = ArrayList()

    fun getAnggotaKeluarga(): ArrayList<AnggotaKeluargaResponse> = dataAnggotaKeluarga

    fun addAnggotaKeluarga(anggota: AnggotaKeluargaResponse) {
        dataAnggotaKeluarga.add(anggota)
    }

    fun removeAnggotaKeluarga(anggota: AnggotaKeluargaResponse) {
        dataAnggotaKeluarga.remove(anggota)
    }

    fun setAnggotaKeluarga() {
        dataPemohon?.anggotaKeluarga = dataAnggotaKeluarga
    }
}