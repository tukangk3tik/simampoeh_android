package app.trikode.simampoeh.ui.akta_lahir.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahAktaLahirBinding
import app.trikode.simampoeh.ui.akta_lahir.tambah.form.AktaLahirDataAnakFragment

class TambahAktaLahirActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahAktaLahirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAktaLahirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AktaLahirDataAnakFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}