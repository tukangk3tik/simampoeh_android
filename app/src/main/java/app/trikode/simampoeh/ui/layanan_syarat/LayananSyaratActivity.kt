package app.trikode.simampoeh.ui.layanan_syarat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.BuildConfig
import app.trikode.simampoeh.databinding.ActivityLayananSyaratBinding

class LayananSyaratActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayananSyaratBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLayananSyaratBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layanan = intent.getStringExtra(LAYANAN).toString()

        val url = "${BuildConfig.WEBVIEW_URL}mobile-layanan-$layanan"
        binding.wvSyarat.loadUrl(url)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        const val LAYANAN = "layanan"
    }
}