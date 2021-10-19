package app.trikode.simampoeh.ui.general_form.upload_berkas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.domain.model.upload_berkas.Berkas
import app.trikode.simampoeh.domain.model.upload_berkas.UploadedBerkas

abstract class FormSubmitViewModel: ViewModel() {

    abstract fun getListBerkas(): LiveData<List<Berkas>>

    abstract fun getUploadedBerkas(): ArrayList<UploadedBerkas>

    abstract fun getLayananId(): String

}