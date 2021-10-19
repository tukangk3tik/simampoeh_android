package app.trikode.simampoeh.ui.akta_pengesahan_anak.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahAktaPengesahanAnakBinding
import app.trikode.simampoeh.ui.akta_pengesahan_anak.tambah.form.DataAnakPengesahanAnakFragment

class TambahAktaPengesahanAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahAktaPengesahanAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAktaPengesahanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DataAnakPengesahanAnakFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}