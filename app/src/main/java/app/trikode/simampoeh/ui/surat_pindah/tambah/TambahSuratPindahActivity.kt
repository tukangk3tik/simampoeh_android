package app.trikode.simampoeh.ui.surat_pindah.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.R
import app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_pemohon.DataPemohonSuratPindahFragment

class TambahSuratPindahActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_surat_pindah)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DataPemohonSuratPindahFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}