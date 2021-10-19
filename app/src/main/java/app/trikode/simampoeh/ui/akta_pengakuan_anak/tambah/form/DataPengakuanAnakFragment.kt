package app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.form

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
import app.trikode.simampoeh.core.data.source.remote.response.akta_pengakuan_anak.AktaPengakuanAnakResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentDataPengakuanAnakBinding
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.TambahAktaPengakuanAnakViewModel
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.konfirmasi.AktaPengakuanAnakKonfirmasiFragment
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.picker.DatePickerFragment
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.jakewharton.rxbinding2.widget.RxTextView
import java.text.SimpleDateFormat
import java.util.*

class DataPengakuanAnakFragment : Fragment(), View.OnClickListener, DatePickerFragment.DialogDateListener {

    private var _binding: FragmentDataPengakuanAnakBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaPengakuanAnakViewModel: TambahAktaPengakuanAnakViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var idLayanan = "2"
    private var idJenisLayanan = "31"
    private var idJenkel = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDataPengakuanAnakBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.edtTglTerbitAktaLahir.setOnClickListener(this)

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

                            idJenkel = it.jenkel.toString()

                            var jenKel = "LAKI-LAKI"
                            if (idJenkel != "1") jenKel = "PEREMPUAN"

                            edtJenkel.setText(jenKel)
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
            binding.edtTglTerbitAktaLahir.id -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.btnNext.id -> {
                val nik = binding.edtNik.text.toString()
                val nama = binding.edtNama.text.toString()
                val jenkel = binding.edtJenkel.text.toString()
                val anakKe = binding.edtAnakKe.text.toString()
                val nomorAkta = binding.edtNoAktaLahir.text.toString()
                val tglTerbitAkta = binding.edtTglTerbitAktaLahir.text.toString()
                val dinasAkta = binding.edtDinasPenerbitAkta.text.toString()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(anakKe) -> binding.edtAnakKe.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(nomorAkta) -> binding.edtNoAktaLahir.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(tglTerbitAkta) -> binding.edtTglTerbitAktaLahir.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(dinasAkta) -> binding.edtDinasPenerbitAkta.error = GeneralUtils.WRONG_FORMAT
                    else -> {
                        val dataBerkas = AktaPengakuanAnakResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            nik = nik,
                            anakNama = nama,
                            anakJenkel = jenkel,
                            anakKe = anakKe,
                            nomorAkta = nomorAkta,
                            tglAkta = tglTerbitAkta,
                            dinasAkta = dinasAkta,
                            anakIdJenkel = idJenkel
                        )

                        tambahAktaPengakuanAnakViewModel.dataPengajuan = dataBerkas
                        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
                        uploadBerkasViewModel.setLayananId(idJenisLayanan)
                        uploadBerkasViewModel.setListBerkas()

                        val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                        mTujuanFragment.setEndFragment(AktaPengakuanAnakKonfirmasiFragment.newInstance())

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

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Siapkan date formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.edtTglTerbitAktaLahir.setText(dateFormat.format(calendar.time).toString())
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"

        @JvmStatic
        fun newInstance() = DataPengakuanAnakFragment()
    }

}