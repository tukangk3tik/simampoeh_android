package app.trikode.simampoeh.ui.akta_kematian.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.R
import app.trikode.simampoeh.ui.akta_kematian.tambah.data_diri.AktaKematianDataDiriFragment

class TambahAktaKematianActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_akta_kematian)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AktaKematianDataDiriFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}