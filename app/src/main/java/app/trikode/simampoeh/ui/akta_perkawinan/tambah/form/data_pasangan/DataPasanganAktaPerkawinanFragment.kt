package app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_pasangan

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
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentAktaPerkawinanPasanganBinding
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanViewModel
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_keluarga.DataKeluargaAktaPerkawinanFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.jakewharton.rxbinding2.widget.RxTextView

class DataPasanganAktaPerkawinanFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAktaPerkawinanPasanganBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaPerkawinanViewModel: TambahAktaPerkawinanViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel

    private var searchNikListenerRoute = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaPerkawinanPasanganBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)

        searchViewModel.pendudukData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        with(binding) {
                            when (searchNikListenerRoute) {
                                "NIK_SUAMI" -> edtNamaSuami.setText(it.nama)
                                "NIK_ISTRI" -> edtNamaIstri.setText(it.nama)
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

        RxTextView.textChanges(binding.edtNikSuami)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_SUAMI"
                    searchViewModel.searchNik(binding.edtNikSuami.text.toString())
                    showNikLoading(true)
                } else {
                    binding.edtNikSuami.error = GeneralUtils.WRONG_FORMAT
                }
            }

        RxTextView.textChanges(binding.edtNikIstri)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_ISTRI"
                    searchViewModel.searchNik(binding.edtNikIstri.text.toString())
                    showNikLoading(true)
                } else {
                    binding.edtNikIstri.error = GeneralUtils.WRONG_FORMAT
                }
            }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val nikSuami = binding.edtNikSuami.text.toString()
                val namaSuami = binding.edtNamaSuami.text.toString()
                val nikIstri = binding.edtNikIstri.text.toString()
                val namaIstri = binding.edtNamaIstri.text.toString()

                when {
                    TextUtils.isEmpty(nikSuami) -> binding.edtNikSuami.error = GeneralUtils.FIELD_REQUIRED
                    (nikSuami.length != 16) -> binding.edtNikSuami.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaSuami) -> binding.edtNamaSuami.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(nikIstri) -> binding.edtNikIstri.error = GeneralUtils.FIELD_REQUIRED
                    (nikSuami.length != 16) -> binding.edtNikIstri.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaIstri) -> binding.edtNamaIstri.error = GeneralUtils.FIELD_REQUIRED
                    else -> {

                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikSuami = nikSuami
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaSuami = namaSuami
                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikIstri = nikIstri
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaIstri = namaIstri

                        val mTujuanFragment = DataKeluargaAktaPerkawinanFragment.newInstance()
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
                                DataPasanganAktaPerkawinanFragment::class.java.simpleName
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

    private fun showNikLoading(state: Boolean){
        when(searchNikListenerRoute) {
            "NIK_SUAMI" -> {
                if (state) binding.progressNikSuami.visibility = View.VISIBLE
                else binding.progressNikSuami.visibility = View.GONE
            }
            "NIK_ISTRI" -> {
                if (state) binding.progressNikIstri.visibility = View.VISIBLE
                else binding.progressNikIstri.visibility = View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
       fun newInstance() = DataPasanganAktaPerkawinanFragment()
    }

}