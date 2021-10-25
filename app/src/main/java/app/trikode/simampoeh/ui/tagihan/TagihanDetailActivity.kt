package app.trikode.simampoeh.ui.tagihan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityTagihanDetailBinding

class TagihanDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTagihanDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTagihanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kodeSts = intent.getStringExtra(KODE_STS).toString()
        val tempo = intent.getStringExtra(TEMPO).toString()
        val layanan = intent.getStringExtra(LAYANAN).toString()

        binding.tvNoSts.text = kodeSts
        binding.tvWaktuBayar.text = tempo
        binding.tvNamaLayanan.text = layanan

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        const val KODE_STS = "kode_sts"
        const val TEMPO = "tempo"
        const val LAYANAN = "layanan"
    }
}