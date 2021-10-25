package app.trikode.simampoeh.ui.tagihan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.model.tagihan.Tagihan
import app.trikode.simampoeh.domain.repository.IAppRepository
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import kotlinx.coroutines.launch

class TagihanViewModel(val appRepository: IAppRepository): ViewModel() {

    private val _listTagihan = MutableLiveData<ApiResponse<ArrayList<Tagihan>>>()
    var listTagihan: LiveData<ApiResponse<ArrayList<Tagihan>>> = _listTagihan

    fun setListTagihan() = viewModelScope.launch {
        _listTagihan.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.getTagihan()

            if (result != null) {
                val data = JsonResponseHelper.tagihanResponseToDomain(result.responseData)
                _listTagihan.postValue(ApiResponse.Success(data))
            } else {
                _listTagihan.postValue(ApiResponse.Error("Gagal mengambil data"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _listTagihan.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

}