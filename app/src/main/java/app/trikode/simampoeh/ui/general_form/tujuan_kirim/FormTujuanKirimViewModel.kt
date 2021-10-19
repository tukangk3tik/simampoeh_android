package app.trikode.simampoeh.ui.general_form.tujuan_kirim

import androidx.lifecycle.ViewModel
import app.trikode.simampoeh.core.data.source.remote.response.TujuanKirim

class FormTujuanKirimViewModel: ViewModel() {

    private var _tujuanKirim = TujuanKirim()

    fun setTujuanKirim(tujuanKirim: TujuanKirim) {
        _tujuanKirim = tujuanKirim
    }

    fun getTujuanKirim(): TujuanKirim = _tujuanKirim

}