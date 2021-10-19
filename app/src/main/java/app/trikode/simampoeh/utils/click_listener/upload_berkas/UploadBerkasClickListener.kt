package app.trikode.simampoeh.utils.click_listener.upload_berkas

import android.view.View
import app.trikode.simampoeh.domain.model.upload_berkas.BerkasResponse

interface UploadBerkasClickListener {

    fun onBerkasItemClicked(view: View, berkas: BerkasResponse, position: Int)

}