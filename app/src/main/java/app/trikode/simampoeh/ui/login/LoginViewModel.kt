package app.trikode.simampoeh.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.model.user.User
import app.trikode.simampoeh.utils.general.EventLiveData
import app.trikode.simampoeh.core.data.source.remote.utils.ResponseResultHandler
import app.trikode.simampoeh.domain.repository.IAppRepository
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val appRepository: IAppRepository): ViewModel() {

    private val _dialogLoginHandler = MutableLiveData<EventLiveData<ResponseResultHandler>>()
    val dialogLoginHandler: LiveData<EventLiveData<ResponseResultHandler>> = _dialogLoginHandler

    private val _userData = MutableLiveData<ApiResponse<User>>()
    val userData: LiveData<ApiResponse<User>> = _userData

    fun login(nik: String, password: String) = viewModelScope.launch {
        _userData.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.login(nik, password)
            if (result != null) {
                val userData = JsonResponseHelper.userResponseToDomain(result.responseData)

                if (userData != null) _userData.postValue(ApiResponse.Success(userData))
                else _userData.postValue(ApiResponse.Error("Fail"))

                _dialogLoginHandler.value = EventLiveData(ResponseResultHandler(result.responseResult, result.responseMessage))
            }
        } catch (e: Exception) {
            _userData.postValue(ApiResponse.Error(e.message))
            _dialogLoginHandler.value = EventLiveData(ResponseResultHandler(0, "Terjadi kesalahan"))
        }
    }

}