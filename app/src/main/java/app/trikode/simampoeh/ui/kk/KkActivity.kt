package app.trikode.simampoeh.ui.kk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityKkBinding
import app.trikode.simampoeh.ui.kk.tambah.TambahKkActivity

class KkActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityKkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahKkActivity::class.java)
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