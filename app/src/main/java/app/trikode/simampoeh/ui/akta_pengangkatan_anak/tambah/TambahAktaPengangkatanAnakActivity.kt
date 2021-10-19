package app.trikode.simampoeh.ui.akta_pengangkatan_anak.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahAktaPengangkatanAnakBinding
import app.trikode.simampoeh.ui.akta_pengangkatan_anak.tambah.form.DataPengangkatanAnakFragment

class TambahAktaPengangkatanAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahAktaPengangkatanAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAktaPengangkatanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DataPengangkatanAnakFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}