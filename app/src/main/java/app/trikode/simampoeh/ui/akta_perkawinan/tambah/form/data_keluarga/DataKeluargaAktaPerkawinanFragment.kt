package app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_keluarga

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
import app.trikode.simampoeh.databinding.FragmentAktaPerkawinanKeluargaBinding
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanViewModel
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_saksi.AktaPerkawinanSaksiFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.jakewharton.rxbinding2.widget.RxTextView

class DataKeluargaAktaPerkawinanFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAktaPerkawinanKeluargaBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaPerkawinanViewModel: TambahAktaPerkawinanViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel

    private var searchNikListenerRoute = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaPerkawinanKeluargaBinding.inflate(inflater, container, false)
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
                            when(searchNikListenerRoute) {
                                "NIK_AYAH_SUAMI" -> edtNamaAyahSuami.setText(it.nama)
                                "NIK_IBU_SUAMI" -> edtNamaIbuSuami.setText(it.nama)
                                "NIK_AYAH_ISTRI" -> edtNamaAyahIstri.setText(it.nama)
                                "NIK_IBU_ISTRI" -> edtNamaIbuIstri.setText(it.nama)
                            }
                        }
                    }
                }
                is ApiResponse.Error -> {
                    response.message?.let {
                        Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is ApiResponse.Loading -> { }
            }
        })

        // RX FOR AYAH SUAMI
        RxTextView.textChanges(binding.edtNikAyahSuami)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_AYAH_SUAMI"
                    searchViewModel.searchNik(binding.edtNikAyahSuami.text.toString())
                } else {
                    binding.edtNikAyahSuami.error = GeneralUtils.WRONG_FORMAT
                }
            }

        // RX FOR IBU SUAMI
        RxTextView.textChanges(binding.edtNikIbuSuami)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_IBU_SUAMI"
                    searchViewModel.searchNik(binding.edtNikIbuSuami.text.toString())
                } else {
                    binding.edtNikIbuSuami.error = GeneralUtils.WRONG_FORMAT
                }
            }

        // RX FOR AYAH ISTRI
        RxTextView.textChanges(binding.edtNikAyahIstri)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_AYAH_ISTRI"
                    searchViewModel.searchNik(binding.edtNikAyahIstri.text.toString())
                } else {
                    binding.edtNikAyahIstri.error = GeneralUtils.WRONG_FORMAT
                }
            }

        // RX FOR IBU ISTRI
        RxTextView.textChanges(binding.edtNikIbuIstri)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "NIK_IBU_ISTRI"
                    searchViewModel.searchNik(binding.edtNikIbuIstri.text.toString())
                } else {
                    binding.edtNikIbuIstri.error = GeneralUtils.WRONG_FORMAT
                }
            }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val nikAyahSuami = binding.edtNikAyahSuami.text.toString()
                val namaAyahSuami = binding.edtNamaAyahSuami.text.toString()
                val nikIbuSuami = binding.edtNikIbuSuami.text.toString()
                val namaIbuSuami = binding.edtNamaIbuSuami.text.toString()

                val nikAyahIstri = binding.edtNikAyahIstri.text.toString()
                val namaAyahIstri = binding.edtNamaAyahIstri.text.toString()
                val nikIbuIstri = binding.edtNikIbuIstri.text.toString()
                val namaIbuIstri = binding.edtNamaIbuIstri.text.toString()

                when {
                    TextUtils.isEmpty(nikAyahSuami) -> binding.edtNikAyahSuami.error = GeneralUtils.FIELD_REQUIRED
                    (nikAyahSuami.length != 16) -> binding.edtNikAyahSuami.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaAyahSuami) -> binding.edtNamaAyahSuami.error = GeneralUtils.FIELD_REQUIRED

                    TextUtils.isEmpty(nikIbuSuami) -> binding.edtNikIbuSuami.error = GeneralUtils.FIELD_REQUIRED
                    (nikIbuSuami.length != 16) -> binding.edtNikIbuSuami.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaIbuSuami) -> binding.edtNamaIbuSuami.error = GeneralUtils.FIELD_REQUIRED

                    TextUtils.isEmpty(nikAyahIstri) -> binding.edtNikAyahIstri.error = GeneralUtils.FIELD_REQUIRED
                    (nikAyahIstri.length != 16) -> binding.edtNikAyahIstri.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaAyahIstri) -> binding.edtNamaAyahIstri.error = GeneralUtils.FIELD_REQUIRED

                    TextUtils.isEmpty(nikIbuIstri) -> binding.edtNikIbuIstri.error = GeneralUtils.FIELD_REQUIRED
                    (nikIbuIstri.length != 16) -> binding.edtNikIbuIstri.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(namaIbuIstri) -> binding.edtNamaIbuIstri.error = GeneralUtils.FIELD_REQUIRED

                    else -> {

                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikSuamiAyah = nikAyahSuami
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaSuamiAyah = namaAyahSuami
                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikSuamiIbu = nikIbuSuami
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaSuamiIbu = namaIbuSuami

                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikIstriAyah = nikAyahIstri
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaIstriAyah = namaAyahIstri
                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikIstriIbu = nikIbuIstri
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaIstriIbu = namaIbuIstri

                        val mTujuanFragment = AktaPerkawinanSaksiFragment.newInstance()
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
                                DataKeluargaAktaPerkawinanFragment::class.java.simpleName
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

    companion object {
        @JvmStatic
        fun newInstance() = DataKeluargaAktaPerkawinanFragment()
    }
}