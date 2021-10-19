package app.trikode.simampoeh.ui.kia.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahKiaBinding
import app.trikode.simampoeh.ui.kia.tambah.form.DataKiaFragment

class TambahKiaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahKiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahKiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DataKiaFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}