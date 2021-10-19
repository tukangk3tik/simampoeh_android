package app.trikode.simampoeh.ui.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.dummy.informasi.listInfo
import app.trikode.simampoeh.core.data.dummy.menu.homeMenu
import app.trikode.simampoeh.domain.model.informasi.Informasi
import app.trikode.simampoeh.domain.model.menu.Menu

class BerandaViewModel : ViewModel() {

    private val _menu = MutableLiveData<List<Menu>>().apply {
        value = homeMenu
    }

    val listMenu : LiveData<List<Menu>> = _menu


    private val _informasi = MutableLiveData<List<Informasi>>().apply {
        value = listInfo
    }

    val listInformasi : LiveData<List<Informasi>> = _informasi
}