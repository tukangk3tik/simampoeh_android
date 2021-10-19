package app.trikode.simampoeh.ui.ktp.tambah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.trikode.simampoeh.databinding.ActivityTambahKtpBinding
import app.trikode.simampoeh.ui.ktp.tambah.form.KtpDataDiriFragment

class TambahKtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahKtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, KtpDataDiriFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}