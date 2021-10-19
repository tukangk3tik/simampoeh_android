package app.trikode.simampoeh.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.domain.repository.IAppRepository
import app.trikode.simampoeh.utils.general.EventLiveData
import com.example.medancapilpelaporan.utils.general.HandlerLiveData
import kotlinx.coroutines.launch

class KirimLayananViewModel(private val appRepository: IAppRepository): ViewModel() {

    private val _submitLayananHandler = MutableLiveData<EventLiveData<HandlerLiveData>>()
    var submitLayananHandler: LiveData<EventLiveData<HandlerLiveData>> = _submitLayananHandler

    fun submitLayanan(request: String, data: String) = viewModelScope.launch {
        //_submitLayananHandler.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.submitLayanan(request, data)

            result?.run {
                if (responseResult > 0){
                    _submitLayananHandler.value = EventLiveData(HandlerLiveData(responseResult, responseMessage))
                } else {
                    setFailUpload("Gagal submit data")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            setFailUpload("Terjadi kesalahan")
        }
    }

    private fun setFailUpload(msg: String){
        _submitLayananHandler.value = EventLiveData(HandlerLiveData(0, msg))
    }

}