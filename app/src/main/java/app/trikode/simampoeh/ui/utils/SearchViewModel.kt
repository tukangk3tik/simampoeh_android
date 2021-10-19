package app.trikode.simampoeh.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.model.user.KartuKeluarga
import app.trikode.simampoeh.domain.model.user.Penduduk
import app.trikode.simampoeh.domain.repository.IAppRepository
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import kotlinx.coroutines.launch


class SearchViewModel(private val appRepository: IAppRepository): ViewModel() {

    private val _pendudukData = MutableLiveData<ApiResponse<Penduduk>>()
    val pendudukData: LiveData<ApiResponse<Penduduk>> = _pendudukData

    fun searchNik(query: String) = viewModelScope.launch {
        _pendudukData.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.cekNik(query)

            if (result != null) {
                val data = JsonResponseHelper.nikResponse(result.responseData)

                if (data != null) _pendudukData.postValue(ApiResponse.Success(data))
                else _pendudukData.postValue(ApiResponse.Error("Data tidak ditemukan"))
            }
        } catch (e: Exception) {
            _pendudukData.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

    private val _anggotaKeluargaData = MutableLiveData<ApiResponse<Penduduk>>()
    val anggotaKeluargaData: LiveData<ApiResponse<Penduduk>> = _anggotaKeluargaData

    fun searchKeluargaByNik(query: String) = viewModelScope.launch {
        _anggotaKeluargaData.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.cekNik(query)

            if (result != null) {
                val data = JsonResponseHelper.nikResponse(result.responseData)

                if (data != null) _anggotaKeluargaData.postValue(ApiResponse.Success(data))
                else _anggotaKeluargaData.postValue(ApiResponse.Error("Data tidak ditemukan"))
            }
        } catch (e: Exception) {
            _anggotaKeluargaData.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

    private val _kartuKeluargaData = MutableLiveData<ApiResponse<KartuKeluarga>>()
    val kartuKeluargaData: LiveData<ApiResponse<KartuKeluarga>> = _kartuKeluargaData

    fun searchKk(query: String) = viewModelScope.launch {
        _kartuKeluargaData.postValue(ApiResponse.Loading())
        try {
            val result = appRepository.getKk(query)

            if (result != null) {
                val data = JsonResponseHelper.kkResponse(result.responseData)

                if (data != null) _kartuKeluargaData.postValue(ApiResponse.Success(data))
                else _kartuKeluargaData.postValue(ApiResponse.Error("Data tidak ditemukan"))
            }
        } catch (e: Exception) {
            _kartuKeluargaData.postValue(ApiResponse.Error("Terjadi kesalahan"))
        }
    }

}