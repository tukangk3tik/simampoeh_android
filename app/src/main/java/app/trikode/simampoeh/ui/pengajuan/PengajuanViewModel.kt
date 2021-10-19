package app.trikode.simampoeh.ui.pengajuan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan.PengajuanResponse
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.repository.IAppRepository
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import kotlinx.coroutines.launch


class PengajuanViewModel(val appRepository: IAppRepository) : ViewModel() {

    private val _listPengajuan = MutableLiveData<ApiResponse<ArrayList<PengajuanResponse>>>()
    var listPengajuan: LiveData<ApiResponse<ArrayList<PengajuanResponse>>> = _listPengajuan

    fun setListPengajuan() = viewModelScope.launch {
        _listPengajuan.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.getHistoryPengajuan()

            if (result != null) {
                val data = JsonResponseHelper.pengajuanToDomainMapper(result.responseData)
                _listPengajuan.postValue(ApiResponse.Success(data))
            } else {
                _listPengajuan.postValue(ApiResponse.Error("Gagal mengambil data"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _listPengajuan.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

}