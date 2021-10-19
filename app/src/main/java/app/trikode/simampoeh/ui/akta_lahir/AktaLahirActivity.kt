package app.trikode.simampoeh.ui.akta_lahir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.trikode.simampoeh.databinding.ActivityAktaLahirBinding
import app.trikode.simampoeh.ui.akta_lahir.tambah.TambahAktaLahirActivity

class AktaLahirActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAktaLahirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaLahirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaLahirActivity::class.java)
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