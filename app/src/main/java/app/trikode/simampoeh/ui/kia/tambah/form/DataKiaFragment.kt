package app.trikode.simampoeh.ui.kia.tambah.form

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
import app.trikode.simampoeh.core.data.source.remote.response.kia.KiaResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentKiaDataBinding
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.kia.tambah.TambahKiaViewModel
import app.trikode.simampoeh.ui.kia.tambah.konfirmasi.KiaKonfirmasiFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.jakewharton.rxbinding2.widget.RxTextView

class DataKiaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentKiaDataBinding? = null
    private val binding get() = _binding!!

    private val tambahKiaViewModel: TambahKiaViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var idLayanan = "10"
    private var idJenisLayanan = "38"
    private var idJenkel = ""
    private var idAgama = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentKiaDataBinding.inflate(inflater, container, false)
        val root = binding.root
        // Inflate the layout for this fragment
        return root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]

        val nikStream = RxTextView.textChanges(binding.edtNik)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }

        nikStream.subscribe { result ->
            if (result) {
                showNikLoading(true)
                searchViewModel.searchNik(binding.edtNik.text.toString())
            } else {
                binding.edtNik.error = GeneralUtils.WRONG_FORMAT
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


                            idJenkel = it.jenkel.toString()

                            //var jenKel = "LAKI-LAKI"
                            if (idJenkel != "1")  {
                                rbPerempuan.isChecked = true
                                //jenKel = "PEREMPUAN"
                            } else {
                                rbLaki.isChecked = true
                            }
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
                val noAktaLahir = binding.edtNoAktaLahir.text.toString()
                val namaAyah = binding.edtNamaAyah.text.toString()
                val namaIbu = binding.edtNamaIbu.text.toString()

                when {
                    TextUtils.isEmpty(noAktaLahir) -> binding.edtNoAktaLahir.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(namaAyah) -> binding.edtNamaAyah.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(namaIbu) -> binding.edtNamaIbu.error = GeneralUtils.FIELD_REQUIRED
                    else -> {
                        val dataBerkas = KiaResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            nik = nik,
                            noAkta = noAktaLahir,
                            anakAyah = namaAyah,
                            anakIbu = namaIbu
                        )

                        tambahKiaViewModel.dataPengajuan = dataBerkas

                        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
                        uploadBerkasViewModel.setLayananId(idJenisLayanan)
                        uploadBerkasViewModel.setListBerkas()

                        val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                        mTujuanFragment.setEndFragment(KiaKonfirmasiFragment.newInstance())

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
                                DataKiaFragment::class.java.simpleName
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
        private const val DATE_PICKER_TAG = "DatePicker"

        @JvmStatic
        fun newInstance() = DataKiaFragment()
    }
}