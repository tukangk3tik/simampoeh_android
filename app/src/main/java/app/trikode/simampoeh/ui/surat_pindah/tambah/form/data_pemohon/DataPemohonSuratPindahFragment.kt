package app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_pemohon

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.surat_pindah.SuratPindahResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentSuratPindahDataPemohonBinding
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahViewModel
import app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_pindah.DataPindahSuratPindahFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.InputUtils
import com.jakewharton.rxbinding2.widget.RxTextView

class DataPemohonSuratPindahFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSuratPindahDataPemohonBinding? = null
    private val binding get() = _binding!!

    private val tambahSuratPindahViewModel: TambahSuratPindahViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var idLayanan = "9"
    private var idJenisLayanan = "51"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuratPindahDataPemohonBinding.inflate(inflater, container, false)
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
                            edtKepalaKeluarga.setText(it.namaKep)

                            searchViewModel.searchKk(it.noKk.toString())
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
                val nama = binding.edtNama.text.toString()
                val noKk = binding.edtKk.text.toString()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    (nik.length != 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    else -> {
                        val dataBerkas = SuratPindahResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            nik = nik,
                            nama = nama,
                            noKk = noKk
                        )

                        tambahSuratPindahViewModel.dataPengajuan = dataBerkas

                        if (uploadBerkasViewModel.getLayananId() != idJenisLayanan) {
                            uploadBerkasViewModel.setLayananId(idJenisLayanan)
                            uploadBerkasViewModel.setListBerkas()
                        }

                        val mTujuanFragment = DataPindahSuratPindahFragment.newInstance()
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
                                DataPemohonSuratPindahFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }
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

    companion object {
        @JvmStatic
        fun newInstance() = DataPemohonSuratPindahFragment()
    }
}