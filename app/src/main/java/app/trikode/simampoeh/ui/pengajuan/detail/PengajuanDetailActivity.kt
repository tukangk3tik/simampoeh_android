package app.trikode.simampoeh.ui.pengajuan.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.BuildConfig
import app.trikode.simampoeh.databinding.ActivityPengajuanDetailBinding

class PengajuanDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengajuanDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPengajuanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = intent?.getStringExtra(UID_PENGAJUAN)
        if (uid != null){
            val url = "${BuildConfig.STAGING_URL}webview_mobile/detail-pengajuan-$uid"
            binding.wvDetail.loadUrl(url)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        const val UID_PENGAJUAN = "uid_pengajuan"
    }
}