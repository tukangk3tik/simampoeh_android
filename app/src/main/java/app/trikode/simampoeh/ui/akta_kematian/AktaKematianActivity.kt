package app.trikode.simampoeh.ui.akta_kematian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.trikode.simampoeh.databinding.ActivityAktaKematianBinding
import app.trikode.simampoeh.ui.akta_kematian.tambah.TambahAktaKematianActivity

class AktaKematianActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAktaKematianBinding
    private val viewModel: AktaKematianViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAktaKematianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTambah.id -> {
                val mIntent = Intent(this, TambahAktaKematianActivity::class.java)
                startActivity(mIntent)
            }
            binding.btnBack.id -> onBackPressed()
        }
    }
}