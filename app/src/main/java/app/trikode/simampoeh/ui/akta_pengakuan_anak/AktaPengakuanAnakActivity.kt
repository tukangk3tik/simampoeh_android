package app.trikode.simampoeh.ui.akta_pengakuan_anak

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityAktaPengakuanAnakBinding
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.TambahAktaPengakuanAnakActivity

class AktaPengakuanAnakActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAktaPengakuanAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaPengakuanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)
        binding.btnTambah.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaPengakuanAnakActivity::class.java)
                startActivity(mIntent)
            }
            binding.btnBack.id -> onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}