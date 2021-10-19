package app.trikode.simampoeh.ui.lupa_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.ResponseResultHandler
import app.trikode.simampoeh.domain.repository.IAppRepository
import app.trikode.simampoeh.utils.general.EventLiveData
import kotlinx.coroutines.launch

class LupaPasswordViewModel(private val appRepository: IAppRepository): ViewModel() {

    private val _dialogLoginHandler = MutableLiveData<EventLiveData<ResponseResultHandler>>()
    val dialogLoginHandler: LiveData<EventLiveData<ResponseResultHandler>> = _dialogLoginHandler

    fun resetPassword(nik: String, nokk: String) = viewModelScope.launch {
        try {
            val result = appRepository.resetPassword(nik, nokk)
            if (result != null) {
                _dialogLoginHandler.value = EventLiveData(ResponseResultHandler(result.responseResult, result.responseMessage))
            }
        } catch (e: Exception) {
            _dialogLoginHandler.value = EventLiveData(ResponseResultHandler(0, "Terjadi kesalahan"))
        }
    }

}