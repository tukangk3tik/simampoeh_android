package app.trikode.simampoeh.ui.main_non_login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.trikode.simampoeh.core.data.dummy.informasi.listInfo
import app.trikode.simampoeh.core.data.dummy.menu.homeMenu
import app.trikode.simampoeh.core.di.Injection
import app.trikode.simampoeh.domain.model.informasi.Informasi
import app.trikode.simampoeh.domain.model.menu.Menu

class MainNonLoginViewModel(application: Application): AndroidViewModel(application) {

    private val remoteDataSource = Injection.provideRemoteDataSource(application)

//    fun cekStatusLayanan(query: String) = viewModelScope.launch {
//        val result = remoteDataSource.cekLayanan(query)
//        d("RESULT_VIEW_MODEL", result.toString())
//    }


    private val _menu = MutableLiveData<List<Menu>>().apply {
        value = homeMenu
    }

    val listMenu : LiveData<List<Menu>> = _menu

    private val _informasi = MutableLiveData<List<Informasi>>().apply {
        value = listInfo
    }

    val listInformasi : LiveData<List<Informasi>> = _informasi

}