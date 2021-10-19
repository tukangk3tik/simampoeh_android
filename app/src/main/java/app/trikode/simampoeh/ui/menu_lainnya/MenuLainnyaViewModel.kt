package app.trikode.simampoeh.ui.menu_lainnya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.dummy.menu.layananMenu
import app.trikode.simampoeh.domain.model.menu.Menu

class MenuLainnyaViewModel: ViewModel() {

    private val _menu = MutableLiveData<List<Menu>>().apply {
        value = layananMenu
    }

    val listMenu : LiveData<List<Menu>> = _menu

}