package app.trikode.simampoeh.ui.ktp.tambah.form

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
import app.trikode.simampoeh.core.data.source.remote.response.ktp.KtpResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentKtpDataDiriBinding
import app.trikode.simampoeh.ui.ktp.tambah.TambahKtpViewModel
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.ktp.tambah.konfirmasi.KtpKonfirmasiFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.InputUtils
import com.jakewharton.rxbinding2.widget.RxTextView

class KtpDataDiriFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentKtpDataDiriBinding? = null
    private val binding get() = _binding!!

    private val tambahKtpViewModel: TambahKtpViewModel by activityViewModels()
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel
    private lateinit var searchViewModel: SearchViewModel

    private var idLayanan = "8"
    private var idJenisLayanan = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout binding for this fragment
        _binding = FragmentKtpDataDiriBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.edtJenisPengajuan.setOnClickListener(this)

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
                            edtTempatLahir.setText(it.tmptLahir)
                            edtTglKelahiran.setText(it.tglLahir)
                            edtAgama.setText(it.agama)

                            var jenkel = "LAKI-LAKI"
                            if (it.jenkel.toString() != "1") jenkel = "PEREMPUAN"
                            edtJenkel.setText(jenkel)
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
            binding.edtJenisPengajuan.id -> {
                GeneralUtils.activityResultLauncher(requireContext(), "pelayanan_jenis",
                    idLayanan, "Pilih Jenis Pelayanan", resultLauncher)
            }
            binding.btnNext.id -> {
                val nik = binding.edtNik.text.toString()
                val jenkel = binding.edtJenkel.text.toString()
                val alasan = binding.edtAlasanPengajuan.text.toString()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    (nik.length != 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    else -> {
                        val dataBerkas = KtpResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            nik = nik,
                            alasan = alasan,
                            jenkel = jenkel
                        )

                        tambahKtpViewModel.dataPengajuan = dataBerkas

                        if (uploadBerkasViewModel.getLayananId() != idJenisLayanan) {
                            uploadBerkasViewModel.setLayananId(idJenisLayanan)
                            uploadBerkasViewModel.setListBerkas()
                        }

                        val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                        mTujuanFragment.setEndFragment(KtpKonfirmasiFragment.newInstance())

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
                                KtpDataDiriFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }
            }
        }
    }

    private fun showNikLoading(state: Boolean) {
        if (state) {
            binding.progressNik.visibility = View.VISIBLE
        } else {
            binding.progressNik.visibility = View.GONE
        }
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
                        idJenisLayanan = idPilihan
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = KtpDataDiriFragment()
    }
}