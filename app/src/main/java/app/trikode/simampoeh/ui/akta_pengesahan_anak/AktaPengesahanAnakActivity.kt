package app.trikode.simampoeh.ui.akta_pengesahan_anak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import app.trikode.simampoeh.databinding.ActivityAktaPengesahanAnakBinding
import app.trikode.simampoeh.ui.akta_pengesahan_anak.tambah.TambahAktaPengesahanAnakActivity

class AktaPengesahanAnakActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAktaPengesahanAnakBinding
    private val viewModel: AktaPengesahanAnakViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaPengesahanAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaPengesahanAnakActivity::class.java)
                startActivity(mIntent)
            }
            binding.btnBack.id -> onBackPressed()
        }
    }
}