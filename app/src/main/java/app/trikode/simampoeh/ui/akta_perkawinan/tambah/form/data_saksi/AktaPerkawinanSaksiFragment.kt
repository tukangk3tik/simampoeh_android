package app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_saksi

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
import app.trikode.simampoeh.databinding.FragmentAktaPerkawinanSaksiBinding
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.form.DataPengakuanAnakFragment
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanViewModel
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.konfirmasi.AktaPerkawinanKonfirmasiFragment
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.general.GeneralUtils
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.jakewharton.rxbinding2.widget.RxTextView

class AktaPerkawinanSaksiFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAktaPerkawinanSaksiBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaPerkawinanViewModel: TambahAktaPerkawinanViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel

    private var searchNikListenerRoute = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaPerkawinanSaksiBinding.inflate(inflater, container, false)
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
                                "SAKSI_1" -> edtNamaSaksi1.setText(it.nama)
                                "SAKSI_2" -> edtNamaSaksi2.setText(it.nama)
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

        RxTextView.textChanges(binding.edtNikSaksi1)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "SAKSI_1"
                    searchViewModel.searchNik(binding.edtNikSaksi1.text.toString())
                } else {
                    binding.edtNikSaksi1.error = GeneralUtils.WRONG_FORMAT
                }
            }

        RxTextView.textChanges(binding.edtNikSaksi2)
            .skipInitialValue()
            .map { nik ->
                nik.length == 16
            }
            .subscribe { result ->
                if (result) {
                    searchNikListenerRoute = "SAKSI_2"
                    searchViewModel.searchNik(binding.edtNikSaksi2.text.toString())
                } else {
                    binding.edtNikSaksi2.error = GeneralUtils.WRONG_FORMAT
                }
            }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val nikSaksi1 = binding.edtNikSaksi1.text.toString()
                val namaSaksi1 = binding.edtNamaSaksi1.text.toString()
                val nikSaksi2 = binding.edtNikSaksi2.text.toString()
                val namaSaksi2 = binding.edtNamaSaksi2.text.toString()

                when {
                    TextUtils.isEmpty(nikSaksi1) -> binding.edtNikSaksi1.text.toString()
                    TextUtils.isEmpty(namaSaksi1) -> binding.edtNamaSaksi1.text.toString()
                    TextUtils.isEmpty(nikSaksi2) -> binding.edtNikSaksi2.text.toString()
                    TextUtils.isEmpty(namaSaksi2) -> binding.edtNamaSaksi2.text.toString()
                    else -> {
                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikSaksi1 = nikSaksi1
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaSaksi1 = namaSaksi1
                        tambahAktaPerkawinanViewModel.dataPengajuan?.nikSaksi2 = nikSaksi2
                        tambahAktaPerkawinanViewModel.dataPengajuan?.namaSaksi2 = namaSaksi2

                        val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                        mTujuanFragment.setEndFragment(AktaPerkawinanKonfirmasiFragment.newInstance())

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
                                DataPengakuanAnakFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AktaPerkawinanSaksiFragment()
    }
}