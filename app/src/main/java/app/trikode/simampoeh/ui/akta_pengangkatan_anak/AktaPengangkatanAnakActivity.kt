package app.trikode.simampoeh.ui.akta_pengangkatan_anak

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityAktaPengangkatanAnakBinding
import app.trikode.simampoeh.ui.akta_pengangkatan_anak.tambah.TambahAktaPengangkatanAnakActivity

class AktaPengangkatanAnakActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityAktaPengangkatanAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaPengangkatanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaPengangkatanAnakActivity::class.java)
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