package app.trikode.simampoeh.ui.kk.tambah.form.data_anggota.tambah

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityTambahAnggotaKkBinding
import app.trikode.simampoeh.domain.model.user.Penduduk
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.InputUtils
import com.jakewharton.rxbinding2.widget.RxTextView

class TambahAnggotaKkActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTambahAnggotaKkBinding
    private lateinit var searchViewModel: SearchViewModel

    private var idShdk = ""

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAnggotaKkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        binding.btnBack.setOnClickListener(this)
        binding.edtShdk.setOnClickListener(this)
        binding.btnTambah.setOnClickListener(this)

        searchViewModel.anggotaKeluargaData.observe(this, pendudukObsever)

        //NIK OBSERVER
        RxTextView.textChanges(binding.edtNik)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    showNikLoading(true)
                    searchViewModel.searchKeluargaByNik(binding.edtNik.text.toString())
                } else {
                    binding.edtNik.error = InputUtils.WRONG_FORMAT
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnBack.id -> finish()
            binding.edtShdk.id -> {
                GeneralUtils.activityResultLauncher(this, "shdk", null, "Pilih SHDK", resultLauncher = resultLauncher)
            }
            binding.btnTambah.id -> {
                val nik = binding.edtNik.text.toString()
                val nama = binding.edtNama.text.toString()
                val namaShdk = binding.edtShdk.text.toString()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(nama) -> binding.edtNama.error = GeneralUtils.FIELD_REQUIRED
                    idShdk == "" -> binding.edtShdk.error = GeneralUtils.FIELD_REQUIRED
                    else -> {
                        val mIntent = Intent()
                        mIntent.putExtra("nik", nik)
                        mIntent.putExtra("nama", nama)
                        mIntent.putExtra("idShdk", idShdk)
                        mIntent.putExtra("namaShdk", namaShdk)
                        setResult(RESULT_OK, mIntent)
                        finish()
                    }
                }
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){
                val pilihan = data.getStringExtra("nama_pilihan").toString()
                val idPilihan = data.getStringExtra("id_pilihan").toString()

                idShdk = idPilihan
                binding.edtShdk.setText(pilihan)
            }
        }
    }

    private val pendudukObsever = Observer<ApiResponse<Penduduk>> { response ->
        when (response) {
            is ApiResponse.Success -> {
                response.data?.let {
                    with(binding) {
                        edtNama.setText(it.nama)
                        btnTambah.isEnabled = true
                    }
                }
                showNikLoading(false)
            }
            is ApiResponse.Error -> {
                response.message?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    binding.btnTambah.isEnabled = false
                }
                showNikLoading(false)
            }
            is ApiResponse.Loading -> { binding.btnTambah.isEnabled = false }
        }
    }

    private fun showNikLoading(state: Boolean) {
        if (state) {
            binding.progressNik.visibility = View.VISIBLE
        } else {
            binding.progressNik.visibility = View.GONE
        }
    }



}