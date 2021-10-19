package app.trikode.simampoeh.ui.kk.tambah.form.data_pemohon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.kk.KartuKeluargaResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentKkDataPemohonBinding
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.kk.tambah.TambahKkViewModel
import app.trikode.simampoeh.ui.kk.tambah.form.data_anggota.DataAnggotaKkFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.option.OptionMenuActivity
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.InputUtils
import com.jakewharton.rxbinding2.widget.RxTextView

class DataPemohonKkFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentKkDataPemohonBinding? = null
    private val binding get() = _binding!!

    private val formViewModel: TambahKkViewModel by activityViewModels()
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel
    private lateinit var searchViewModel: SearchViewModel

    private var idLayanan = "7"
    private var idJenisPengajuan = ""
    private var idProvinsi = ""
    private var idKabupaten = ""
    private var idKecamatan = ""
    private var idKelurahan = ""
    private var idShdk = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKkDataPemohonBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.edtJenisPengajuan.setOnClickListener(this)
        binding.edtKecamatan.setOnClickListener(this)
        binding.edtKelurahan.setOnClickListener(this)
        binding.edtShdk.setOnClickListener(this)

        RxTextView.textChanges(binding.edtNik)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    showNikLoading(true)
                    searchViewModel.searchNik(binding.edtNik.text.toString())
                } else {
                    binding.edtNik.error = InputUtils.WRONG_FORMAT
                }
            }

        searchViewModel.pendudukData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        with(binding) {
                            edtNama.setText(it.nama)
                            edtKk.setText(it.noKk)

                            idProvinsi = it.provinsi.toString()
                            idKabupaten = it.provinsi.toString()
                            idKecamatan = it.kecamatan.toString()
                            idKelurahan = it.kelurahan.toString()
                            edtKecamatan.setText(it.namaKecamatan)
                            edtKelurahan.setText(it.namaKelurahan)
                        }
                    }
                    showNikLoading(false)
                }
                is ApiResponse.Error -> {
                    response.message?.let {
                        Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
                    }
                    showNikLoading(false)
                }
                is ApiResponse.Loading -> { }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> activity?.finish()
            binding.btnNext.id -> {
                val nik = binding.edtNik.text.toString()
                val nokk = binding.edtKk.text.toString()
                val nama = binding.edtNama.text.toString()
                val alamat = binding.edtAlamat.text.toString()
                val alasan = binding.edtAlasanLain.text.toString()
                val kecamatanPemohon = binding.edtKecamatan.text.toString()
                val kelurahanPemohon = binding.edtKelurahan.text.toString()

                when {
                    idJenisPengajuan == "" -> binding.edtJenisPengajuan.error = InputUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = InputUtils.FIELD_REQUIRED
                    idKecamatan == "" -> binding.edtKecamatan.error = InputUtils.FIELD_REQUIRED
                    idKelurahan == "" -> binding.edtKelurahan.error = InputUtils.FIELD_REQUIRED
                    else -> {

                        //SET DATA PEMOHON
                        val dataPemohon = KartuKeluargaResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisPengajuan,
                            nik = nik,
                            nama = nama,
                            idShdk = idShdk,
                            idKecamatanPemohon = "$idKecamatan|$kecamatanPemohon",
                            idKelurahanPemohon = "$idKelurahan|$kelurahanPemohon",
                            alamatPemohon = alamat,
                            alasanPermohonan = alasan,
                            noKkLama = nokk
                        )
                        //SEND TO VIEW MODEL
                        formViewModel.dataPemohon = dataPemohon

                        val mTujuanFragment = DataAnggotaKkFragment.newInstance()
                        val mFragmentManager = parentFragmentManager

                        mFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                            )
                            replace(
                                R.id.container,
                                mTujuanFragment,
                                DataPemohonKkFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }
            }
            binding.edtJenisPengajuan.id -> {
                showOptionMenu("pelayanan_jenis", "7","Pilih Jenis Pelayanan")
            }
            binding.edtKecamatan.id -> {
                showOptionMenu("kecamatan", null,"Pilih Kecamatan")
            }
            binding.edtKelurahan.id -> {
                if (idKecamatan == "") {
                    AlertDialogHelper.showAlertDialog("Pilih Kecamatan lebih dulu", requireContext())
                } else {
                    showOptionMenu("kelurahan", idKecamatan,"Pilih Kelurahan")
                }
            }
            binding.edtShdk.id -> {
                showOptionMenu("shdk", null,"Pilih SHDK")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showNikLoading(state: Boolean) {
        if (state) {
            binding.progressNik.visibility = View.VISIBLE
        } else {
            binding.progressNik.visibility = View.GONE
        }
    }

    private fun showOptionMenu(menuOption: String, parent: String?, title: String) {
        val mOptionIntent = Intent(binding.root.context, OptionMenuActivity::class.java)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_OPTION, menuOption)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_PARENT, parent)
        mOptionIntent.putExtra(OptionMenuActivity.TITLE, title)
        resultLauncher.launch(mOptionIntent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){
                val opsi = data.getStringExtra("menu_option").toString()
                val pilihan = data.getStringExtra("nama_pilihan").toString()
                val idPilihan = data.getStringExtra("id_pilihan").toString()

                when (opsi) {
                    "pelayanan_jenis" -> {
                        binding.edtJenisPengajuan.setText(pilihan)
                        formViewModel.setJenisLayananId(idPilihan)

                        idJenisPengajuan = idPilihan
                        uploadBerkasViewModel.setLayananId(idJenisPengajuan)
                        uploadBerkasViewModel.setListBerkas()
                    }
                    "shdk" -> {
                        binding.edtShdk.setText(pilihan)
                        idShdk = idPilihan
                    }
                    "kecamatan" -> {
                        binding.edtKecamatan.setText(pilihan)
                        idKecamatan = idPilihan

                        binding.edtKelurahan.setText(null)
                    }
                    "kelurahan" -> {
                        binding.edtKelurahan.setText(pilihan)
                        idKelurahan = idPilihan
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DataPemohonKkFragment()
    }

}