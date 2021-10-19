package app.trikode.simampoeh.ui.akta_lahir

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.dummy.aktaLahirBerkasList
import app.trikode.simampoeh.domain.model.upload_berkas.Berkas

class AktaLahirViewModel : ViewModel() {

    private val _listBerkas = MutableLiveData<List<Berkas>>(). apply {
        value = aktaLahirBerkasList
    }

    fun getListBerkas(): LiveData<List<Berkas>> = _listBerkas

}