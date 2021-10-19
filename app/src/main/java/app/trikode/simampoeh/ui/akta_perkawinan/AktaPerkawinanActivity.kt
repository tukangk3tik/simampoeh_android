package app.trikode.simampoeh.ui.akta_perkawinan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityAktaPerkawinanBinding
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanActivity

class AktaPerkawinanActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAktaPerkawinanBinding
    private val viewModel: AktaPerkawinanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaPerkawinanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaPerkawinanActivity::class.java)
                startActivity(mIntent)
            }
            binding.btnBack.id -> onBackPressed()
        }
    }
}