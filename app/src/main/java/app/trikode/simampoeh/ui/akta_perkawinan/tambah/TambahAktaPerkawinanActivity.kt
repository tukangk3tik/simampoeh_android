package app.trikode.simampoeh.ui.akta_perkawinan.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahAktaPerkawinanBinding
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_form.DataAktaPerkawinanFragment

class TambahAktaPerkawinanActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTambahAktaPerkawinanBinding
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAktaPerkawinanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            position = 0

            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DataAktaPerkawinanFragment.newInstance())
                .commitNow()
        }
    }

}