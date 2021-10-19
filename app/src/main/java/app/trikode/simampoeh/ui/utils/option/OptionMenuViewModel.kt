package app.trikode.simampoeh.ui.utils.option

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.model.option.OptionMenu
import app.trikode.simampoeh.domain.repository.IAppRepository
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import kotlinx.coroutines.launch

class OptionMenuViewModel(val appRepository: IAppRepository) : ViewModel() {

    private val _optionMenu = MutableLiveData<ApiResponse<ArrayList<OptionMenu>>>()
    var optionMenu: LiveData<ApiResponse<ArrayList<OptionMenu>>> = _optionMenu

    fun setOptionMenu(request: String?, parent: String?) = viewModelScope.launch {
        _optionMenu.postValue(ApiResponse.Loading())
        try {
            if (request != null) {
                val result = appRepository.loadMaster(request, parent)
                if (result != null){
                    val dataOption = JsonResponseHelper.optionMenuResponseToDomain(result.responseData)
                    _optionMenu.postValue(ApiResponse.Success(dataOption))
                }
            } else {
                setGagalResponse()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            _optionMenu.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

    fun setWilayah(
        request: String?,
        provinsi: String?,
        kabupaten: String?,
        kecamatan: String?
    ) = viewModelScope.launch {
        _optionMenu.postValue(ApiResponse.Loading())
        try {
            if (request != null) {
                val result = appRepository.loadWilayah(request, provinsi, kabupaten, kecamatan)
                if (result != null){
                    val dataOption = JsonResponseHelper.optionMenuResponseToDomain(result.responseData)
                    _optionMenu.postValue(ApiResponse.Success(dataOption))
                }
            } else {
                setGagalResponse()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            _optionMenu.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

    private fun setGagalResponse() {
        _optionMenu.postValue(ApiResponse.Error("Gagal mengambil data"))
    }
}