package app.trikode.simampoeh.ui.akta_kematian.tambah.data_diri

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.akta_kematian.AktaKematianResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentAktaKematianDataDiriBinding
import app.trikode.simampoeh.ui.akta_kematian.tambah.TambahAktaKematianViewModel
import app.trikode.simampoeh.ui.akta_kematian.tambah.data_alamat.AktaKematianDataAlamatFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.picker.DatePickerFragment
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import java.text.SimpleDateFormat
import java.util.*

class AktaKematianDataDiriFragment : Fragment(), View.OnClickListener, DatePickerFragment.DialogDateListener {

    private var _binding: FragmentAktaKematianDataDiriBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaKematianViewModel: TambahAktaKematianViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var idLayanan = "4"
    private var idJenisLayanan = "33"
    private var idAgama = ""
    private var idKewarganegaraan = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaKematianDataDiriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.edtTglKelahiran.setOnClickListener(this)
        binding.edtAgama.setOnClickListener(this)
        binding.edtKewarganegaraan.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> activity?.finish()
            binding.edtTglKelahiran.id -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.edtAgama.id -> {
                GeneralUtils.activityResultLauncher(
                    binding.root.context,
                    "agama", "0",
                    "Pilih Agama", resultLauncher)
            }
            binding.edtKewarganegaraan.id -> {
                GeneralUtils.activityResultLauncher(
                    binding.root.context,
                    "kewarganegaraan", "0",
                    "Pilih Kewarganegaraan", resultLauncher)
            }
            binding.btnNext.id -> {
                val hubungan = binding.edtHubungan.text.toString()
                val nama = binding.edtNamaLengkap.text.toString()
                val nik = binding.edtNik.text.toString()
                val tempatLahir = binding.edtTempatLahir.text.toString()
                val tglLahir = binding.edtTglKelahiran.text.toString()

                var jenKel = ""
                when(binding.rgJenkel.checkedRadioButtonId) {
                    binding.rbLaki.id -> jenKel = "1"
                    binding.rbPerempuan.id -> jenKel = "2"
                }

                when {
                    TextUtils.isEmpty(hubungan) -> binding.edtHubungan.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(nama) -> binding.edtNamaLengkap.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    (nik.length != 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT

                    (jenKel == "") -> AlertDialogHelper.showAlertDialog("Pilih jenis kelamin", requireContext())
                    TextUtils.isEmpty(tempatLahir) -> binding.edtTempatLahir.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(tglLahir) -> binding.edtTglKelahiran.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(idAgama) -> binding.edtAgama.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(idKewarganegaraan) -> binding.edtKewarganegaraan.error = GeneralUtils.FIELD_REQUIRED

                    else -> {

                        val dataBerkas = AktaKematianResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            hubungan = hubungan,
                            nama = nama,
                            idJenkel = jenKel,
                            tempatLahir = tempatLahir,
                            tanggalLahir = tglLahir,
                            idAgama = idAgama,
                            idKewarganegaraan = idKewarganegaraan
                        )

                        tambahAktaKematianViewModel.dataPengajuan = dataBerkas
                        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
                        uploadBerkasViewModel.setLayananId(idJenisLayanan)
                        uploadBerkasViewModel.setListBerkas()

                        val mTujuanFragment = AktaKematianDataAlamatFragment.newInstance()
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
                                AktaKematianDataDiriFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }


            }
        }
    }


    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Siapkan date formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.edtTglKelahiran.setText(dateFormat.format(calendar.time).toString())
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){
                val opsi = data.getStringExtra("menu_option").toString()
                val pilihan = data.getStringExtra("nama_pilihan").toString()
                val idPilihan = data.getStringExtra("id_pilihan").toString()

                when (opsi) {
                    "agama" -> {
                        binding.edtAgama.setText(pilihan)
                        idAgama = idPilihan
                    }
                    "kewarganegaraan" -> {
                        binding.edtKewarganegaraan.setText(pilihan)
                        idKewarganegaraan = idPilihan
                    }
                }
            }
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"

        @JvmStatic
        fun newInstance() = AktaKematianDataDiriFragment()
    }

}