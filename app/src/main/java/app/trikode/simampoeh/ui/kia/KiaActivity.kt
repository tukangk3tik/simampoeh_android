package app.trikode.simampoeh.ui.kia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityKiaBinding
import app.trikode.simampoeh.ui.kia.tambah.TambahKiaActivity

class KiaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityKiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahKiaActivity::class.java)
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