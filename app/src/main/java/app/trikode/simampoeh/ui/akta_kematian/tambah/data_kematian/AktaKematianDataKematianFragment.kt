package app.trikode.simampoeh.ui.akta_kematian.tambah.data_kematian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.FragmentAktaKematianDataKematianBinding
import app.trikode.simampoeh.ui.akta_kematian.tambah.TambahAktaKematianViewModel
import app.trikode.simampoeh.ui.akta_kematian.tambah.konfirmasi.AktaKematianKonfirmasiFragment
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.utils.picker.DatePickerFragment
import app.trikode.simampoeh.ui.utils.picker.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AktaKematianDataKematianFragment : Fragment(), View.OnClickListener,
    DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener
{

    private var _binding: FragmentAktaKematianDataKematianBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaKematianViewModel: TambahAktaKematianViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaKematianDataKematianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.edtTglMeninggal.setOnClickListener(this)
        binding.edtPukul.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.edtTglMeninggal.id -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.mListener = this
                datePickerFragment.show(childFragmentManager, DATE_PICKER_TAG)
            }
            binding.edtPukul.id -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.mListener = this
                timePickerFragment.show(childFragmentManager, TIME_PICKER_TAG)
            }
            binding.btnNext.id -> {
                val tglMeninggal = binding.edtTglMeninggal.text.toString()
                val pukul = binding.edtPukul.text.toString()
                val tempatMeninggal = binding.edtTempatMeninggal.text.toString()
                val sebabMeninggal = binding.edtSebabKematian.text.toString()

                tambahAktaKematianViewModel.dataPengajuan?.tanggalMeninggal = tglMeninggal
                tambahAktaKematianViewModel.dataPengajuan?.jamMeninggal = pukul
                tambahAktaKematianViewModel.dataPengajuan?.tempatMeninggal = tempatMeninggal
                tambahAktaKematianViewModel.dataPengajuan?.penyebabMeninggal = sebabMeninggal

                val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                mTujuanFragment.setEndFragment(AktaKematianKonfirmasiFragment.newInstance())

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
                        AktaKematianDataKematianFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Siapkan date formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.edtTglMeninggal.setText(dateFormat.format(calendar.time).toString())
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.edtPukul.setText(dateFormat.format(calendar.time).toString())
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_TAG = "TimePicker"

        @JvmStatic
        fun newInstance() = AktaKematianDataKematianFragment()
    }

}