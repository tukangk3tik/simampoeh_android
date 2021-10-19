package app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTambahAktaPengakuanAnakBinding
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.form.DataPengakuanAnakFragment

class TambahAktaPengakuanAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahAktaPengakuanAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAktaPengakuanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DataPengakuanAnakFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}