package app.trikode.simampoeh.ui.kk.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.R
import app.trikode.simampoeh.ui.kk.tambah.form.data_pemohon.DataPemohonKkFragment

class TambahKkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_kk)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DataPemohonKkFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}