package app.trikode.simampoeh.ui.akta_lahir.tambah.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.akta_lahir.AktaLahirResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentAktaLahirDataAnakBinding
import app.trikode.simampoeh.ui.akta_lahir.tambah.TambahAktaLahirViewModel
import app.trikode.simampoeh.ui.akta_lahir.tambah.konfirmasi.AktaLahirKonfirmasiFragment
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.option.OptionMenuActivity
import app.trikode.simampoeh.ui.utils.picker.DatePickerFragment
import app.trikode.simampoeh.ui.utils.picker.TimePickerFragment
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import java.text.SimpleDateFormat
import java.util.*

class AktaLahirDataAnakFragment :
    Fragment(),
    View.OnClickListener,
    DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener
{

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_TAG = "TimePicker"

        fun newInstance() = AktaLahirDataAnakFragment()
    }

    private val tambahAktaLahirviewModel: TambahAktaLahirViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private var _binding: FragmentAktaLahirDataAnakBinding? = null
    private val binding get() = _binding!!

    private var idLayanan = "3"
    private var idJenisLayanan = "34"
    private var idTempatDilahirkan = ""
    private var idPenolongKelahiran = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAktaLahirDataAnakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.edtTglKelahiran.setOnClickListener(this)
        binding.edtJamKelahiran.setOnClickListener(this)
        binding.edtTempatDilahirkan.setOnClickListener(this)
        binding.edtPenolongKelahiran.setOnClickListener(this)

        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.kembar,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerJenisKelahiran.adapter = adapter
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> activity?.finish()
            binding.btnNext.id -> {
                val namaLengkap = binding.edtNama.text.toString()
                val tempatLahir = binding.edtTempatLahir.text.toString()
                val tanggalLahir = binding.edtTglKelahiran.text.toString()
                val jamKelahiran = binding.edtJamKelahiran.text.toString()
                val kelahiranKe = binding.edtKelahiranKe.text.toString()
                val beratBayi = binding.edtBeratBayi.text.toString()
                val panjangBayi = binding.edtPanjangBayi.text.toString()
                val noHp = binding.edtNoHp.text.toString()
                val namaAyah = binding.edtAyah.text.toString()
                val namaIbu = binding.edtIbu.text.toString()
                val saksi1 = binding.edtSaksiI.text.toString()
                val saksi2 = binding.edtSaksiIi.text.toString()
                val jenisKelahiran = binding.spinnerJenisKelahiran.selectedItem.toString()

                var jenKel = ""
                when(binding.rgJenkel.checkedRadioButtonId) {
                    binding.rbLaki.id -> jenKel = "1"
                    binding.rbPerempuan.id -> jenKel = "2"
                }

                when {
                    TextUtils.isEmpty(namaLengkap) -> binding.edtNama.error = GeneralUtils.FIELD_REQUIRED
                    (jenKel == "") -> AlertDialogHelper.showAlertDialog("Pilih jenis kelamin", requireContext())
                    idTempatDilahirkan == "" -> binding.edtTempatDilahirkan.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(tempatLahir) -> binding.edtTempatLahir.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(tanggalLahir) -> binding.edtTglKelahiran.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(jamKelahiran) -> binding.edtJamKelahiran.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(kelahiranKe) -> binding.edtKelahiranKe.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(beratBayi) -> binding.edtBeratBayi.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(panjangBayi) -> binding.edtPanjangBayi.error = GeneralUtils.FIELD_REQUIRED
                    idPenolongKelahiran == "" -> binding.edtPenolongKelahiran.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(noHp) -> binding.edtNoHp.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(namaAyah) -> binding.edtAyah.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(namaIbu) -> binding.edtIbu.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(saksi1) -> binding.edtSaksiI.error = GeneralUtils.FIELD_REQUIRED
                    TextUtils.isEmpty(saksi2) -> binding.edtSaksiIi.error = GeneralUtils.FIELD_REQUIRED
                    else -> {
                        val dataBerkas = AktaLahirResponse(
                            idPelayanan = idLayanan,
                            idJenis = idJenisLayanan,
                            anakNama = namaLengkap,
                            anakTempatLahir = tempatLahir,
                            anakBeratBayi = beratBayi,
                            anakPanjangBayi = panjangBayi,
                            anakIdJenkel = jenKel,
                            anakIdTempatKelahiran = idTempatDilahirkan,
                            anakTglLahir = tanggalLahir,
                            anakJamLahir = jamKelahiran,
                            kelahiranKe = kelahiranKe,
                            idPenolongKelahiran = idPenolongKelahiran,
                            namaAyah = namaAyah,
                            namaIbu = namaIbu,
                            namaSaksi1 = saksi1,
                            namaSaksi2 = saksi2,
                            jenisKelahiran = jenisKelahiran,
                            noHp = noHp
                        )

                        tambahAktaLahirviewModel.dataPengajuan = dataBerkas

                        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
                        uploadBerkasViewModel.setLayananId(idJenisLayanan)
                        uploadBerkasViewModel.setListBerkas()

                        val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                        mTujuanFragment.setEndFragment(AktaLahirKonfirmasiFragment.newInstance())

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
                                AktaLahirDataAnakFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                        }
                    }
                }

            }
            binding.edtTempatDilahirkan.id -> {
                showOptionMenu("tempat_dilahirkan", "Pilih Tempat Dilahirkan")
            }
            binding.edtPenolongKelahiran.id -> {
                showOptionMenu("penolong_kelahiran", "Pilih Penolong Kelahiran")
            }
            binding.edtTglKelahiran.id -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.edtJamKelahiran.id -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.mListener = this
                timePickerFragment.show(childFragmentManager, TIME_PICKER_TAG)
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

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.edtJamKelahiran.setText(dateFormat.format(calendar.time).toString())
    }

    private fun showOptionMenu(menuOption: String, title: String) {
        val mOptionIntent = Intent(binding.root.context, OptionMenuActivity::class.java)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_OPTION, menuOption)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_PARENT, 0)
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
                    "tempat_dilahirkan" -> {
                        binding.edtTempatDilahirkan.setText(pilihan)
                        idTempatDilahirkan = idPilihan
                    }
                    "penolong_kelahiran" -> {
                        binding.edtPenolongKelahiran.setText(pilihan)
                        idPenolongKelahiran = idPilihan
                    }
                }
            }
        }
    }


}