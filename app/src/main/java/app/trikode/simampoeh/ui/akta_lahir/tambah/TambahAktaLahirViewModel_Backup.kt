package app.trikode.simampoeh.ui.akta_lahir.tambah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.trikode.simampoeh.domain.model.layanan.AktaLahir
import app.trikode.simampoeh.domain.model.upload_berkas.Berkas
import app.trikode.simampoeh.domain.model.upload_berkas.UploadedBerkas
import app.trikode.simampoeh.domain.repository.IAppRepository
import app.trikode.simampoeh.ui.general_form.FormViewModel
import retrofit2.http.Part

class TambahAktaLahirViewModel_Backup(val appRepository: IAppRepository): FormViewModel() {

    val _uploadedBerkas = ArrayList<UploadedBerkas>()

    fun tambahBerkas(key: String, foto: Part) {
        _uploadedBerkas.add(UploadedBerkas(key, foto))
    }

    override fun getUploadedBerkas(): ArrayList<UploadedBerkas> = _uploadedBerkas

    val listBerkas = MutableLiveData<List<Berkas>>()

    override fun getListBerkas(): LiveData<List<Berkas>> = listBerkas


    /**
     * FORM DATA KEEPER
     */
    private val _formData = MutableLiveData<AktaLahir>()

    fun setFormData(aktaLahir: AktaLahir) {
        _formData.value = aktaLahir
    }

}