package app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_form

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
import app.trikode.simampoeh.core.data.source.remote.response.akta_perkawinan.AktaPerkawinanResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentAktaPerkawinanDataBinding
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanViewModel
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.form.data_pasangan.DataPasanganAktaPerkawinanFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.option.OptionMenuActivity
import app.trikode.simampoeh.ui.utils.picker.DatePickerFragment
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import java.text.SimpleDateFormat
import java.util.*

class DataAktaPerkawinanFragment : Fragment(), View.OnClickListener, DatePickerFragment.DialogDateListener {

    private var _binding: FragmentAktaPerkawinanDataBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaPerkawinanViewModel: TambahAktaPerkawinanViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var idPelayanan = "5"
    private var idJenisLayanan = "36"
    private var idAgama = ""
    private var dateField = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaPerkawinanDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.edtAgama.setOnClickListener(this)
        binding.edtTglPemberkatan.setOnClickListener(this)
        binding.edtTglPutusanPengadilan.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> activity?.finish()
            binding.edtAgama.id -> {
                val mOptionIntent = Intent(binding.root.context, OptionMenuActivity::class.java)
                mOptionIntent.putExtra(OptionMenuActivity.MENU_OPTION, "agama")
                mOptionIntent.putExtra(OptionMenuActivity.MENU_PARENT, 0)
                mOptionIntent.putExtra(OptionMenuActivity.TITLE, "Pilih Agama")
                resultLauncher.launch(mOptionIntent)
            }
            binding.edtTglPemberkatan.id -> {
                dateField = "perkawinan"
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.edtTglPutusanPengadilan.id -> {
                dateField = "pengadilan"
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.btnNext.id -> {
                val tglPerkawinan = binding.edtTglPemberkatan.text.toString()
                val agama = binding.edtAgama.text.toString()
                val organisasiPenghayat = binding.edtOrganisasiKepercayaan.text.toString()
                val namaBadanPeradilan = binding.edtBadanPeradilan.text.toString()
                val nomorPengadilan = binding.edtNoPutusanPengadilan.text.toString()
                val tglPengadilan = binding.edtTglPutusanPengadilan.text.toString()
                val namaPemukaAgama = binding.edtNamaPemukaAgama.text.toString()
                val ijinWna = binding.edtNoIjinPerwakilan.text.toString()
                val jlhAnak = binding.edtJlhAnak.text.toString()

                when {
                    TextUtils.isEmpty(tglPerkawinan) -> binding.edtTglPemberkatan.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(agama) -> binding.edtAgama.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(organisasiPenghayat) -> binding.edtOrganisasiKepercayaan.error = GeneralUtils.FIELD_REQUIRED
                    else -> {

                        val dataPerkawinan = AktaPerkawinanResponse(
                            idPelayanan = idPelayanan,
                            idJenis = idJenisLayanan,
                            idAgama = idAgama,
                            tanggalKawin = tglPerkawinan,
                            organisasiPenghayat = organisasiPenghayat,
                            badanPeradilan = namaBadanPeradilan,
                            nomorPengadilan = nomorPengadilan,
                            tanggalPengadilan = tglPengadilan,
                            namaPemukaAgama = namaPemukaAgama,
                            izinPerwakilanWna = ijinWna,
                            jumlahAnak = jlhAnak
                        )

                        tambahAktaPerkawinanViewModel.dataPengajuan = dataPerkawinan

                        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
                        uploadBerkasViewModel.setLayananId(idJenisLayanan)
                        uploadBerkasViewModel.setListBerkas()

                        val mTujuanFragment = DataPasanganAktaPerkawinanFragment.newInstance()
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
                                DataAktaPerkawinanFragment::class.java.simpleName
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
        when(dateField) {
            "perkawinan" -> binding.edtTglPemberkatan.setText(dateFormat.format(calendar.time).toString())
            "pengadilan" -> binding.edtTglPutusanPengadilan.setText(dateFormat.format(calendar.time).toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
                }
            }
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"

        @JvmStatic
       fun newInstance() = DataAktaPerkawinanFragment()
    }


}