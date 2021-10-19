package app.trikode.simampoeh.ui.general_form.upload_berkas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trikode.simampoeh.core.data.source.remote.utils.JsonResponseHelper
import app.trikode.simampoeh.domain.model.upload_berkas.BerkasResponse
import app.trikode.simampoeh.domain.repository.IAppRepository
import app.trikode.simampoeh.utils.general.EventLiveData
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.HandlerLiveData
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadBerkasViewModel(val appRepository: IAppRepository): ViewModel() {

    private var _layananId = ""

    fun setLayananId(id: String) {
        _layananId = id
    }

    fun getLayananId(): String = _layananId

    private val _listBerkas = MutableLiveData<ApiResponse<ArrayList<BerkasResponse>>>()
    var listBerkas: LiveData<ApiResponse<ArrayList<BerkasResponse>>> = _listBerkas

    private lateinit var berkasUploaded : ArrayList<BerkasResponse>

    fun setListBerkas() = viewModelScope.launch {
        if (_layananId != "") {
            _listBerkas.postValue(ApiResponse.Loading())
            try {
                val result = appRepository.getListBerkas(_layananId)

                if (result != null) {
                    val data = JsonResponseHelper.berkasToDomainMapper(result.responseData)
                    _listBerkas.postValue(ApiResponse.Success(data))
                    berkasUploaded = data
                } else {
                    _listBerkas.postValue(ApiResponse.Error("Gagal mengambil data"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _listBerkas.postValue(ApiResponse.Error("Terjadi kesalahan"))
            }
        }
    }

    fun uploadBerkas(
        imageToUpload: MultipartBody.Part,
        fileName: RequestBody,
        type: RequestBody,
        request: RequestBody,
        inListPosition: Int
    ) = viewModelScope.launch {
        try {
            val result = appRepository.uploadBerkasPartial(imageToUpload, fileName, type, request)
            val data = _listBerkas.value?.data
            _listBerkas.postValue(ApiResponse.Loading())

            result?.run {
                if (responseResult > 0){
                    _uploadResultHandler.value = EventLiveData(HandlerLiveData(responseResult, responseMessage))
                    val getUrl = JsonResponseHelper.getUploadBerkasUrlString(responseData)

                    if (data != null){
                        data[inListPosition].urlGambar = getUrl
                        _listBerkas.postValue(ApiResponse.Success(data))
                    }
                } else {
                    setFailUpload("Gagal upload berkas")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            setFailUpload("Terjadi kesalahan")
        }
    }

    private val _uploadResultHandler = MutableLiveData<EventLiveData<HandlerLiveData>>()
    var uploadResultHandler: LiveData<EventLiveData<HandlerLiveData>> = _uploadResultHandler

    private fun setFailUpload(msg: String){
        _uploadResultHandler.value = EventLiveData(HandlerLiveData(0, msg))
    }
}