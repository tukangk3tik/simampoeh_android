package app.trikode.simampoeh.ui.surat_pindah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.trikode.simampoeh.databinding.ActivitySuratPindahBinding
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahActivity

class SuratPindahActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySuratPindahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuratPindahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> onBackPressed()
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahSuratPindahActivity::class.java)
                startActivity(mIntent)
            }
        }
    }
}