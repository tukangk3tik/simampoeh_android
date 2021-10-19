package app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_alasan_pindah

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.FragmentSuratPindahDataAlasanBinding
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahViewModel
import app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_pindah.DataPindahSuratPindahFragment
import app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_keluarga.DataKeluargaSuratPindahFragment
import app.trikode.simampoeh.utils.general.GeneralUtils

class DataAlasanSuratPindahFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSuratPindahDataAlasanBinding? = null
    private val binding get() = _binding!!

    private val formViewModel: TambahSuratPindahViewModel by activityViewModels()

    private var idAlasan = ""
    private var idJenisPindah = ""
    private var idStatusKkPindah = ""
    private var idStatusKkTidakPindah = ""

    private var statusField = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuratPindahDataAlasanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.edtAlasanPindah.setOnClickListener(this)
        binding.edtJenisKepindahan.setOnClickListener(this)
        binding.edtStatusKkPindah.setOnClickListener(this)
        binding.edtStatusKkTidakPindah.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.edtAlasanPindah.id -> {
                GeneralUtils.activityResultLauncher(requireContext(), "alasan_pindah",
                    "", "Pilih Alasan Pindah", resultLauncher)
            }
            binding.edtJenisKepindahan.id -> {
                GeneralUtils.activityResultLauncher(requireContext(), "jenis_kepindahan",
                    "", "Pilih Jenis Kepindahan", resultLauncher)
            }
            binding.edtStatusKkPindah.id -> {
                statusField = "kk_pindah"
                GeneralUtils.activityResultLauncher(requireContext(), "status_pindah_kk",
                    "", "Pilih Status", resultLauncher)
            }
            binding.edtStatusKkTidakPindah.id -> {
                statusField = "kk_tidak_pindah"
                GeneralUtils.activityResultLauncher(requireContext(), "status_pindah_kk",
                    "", "Pilih Status", resultLauncher)
            }
            binding.btnNext.id -> {
                val mTujuanFragment = DataKeluargaSuratPindahFragment.newInstance()
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
                        DataPindahSuratPindahFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){
                val opsi = data.getStringExtra("menu_option").toString()
                val pilihan = data.getStringExtra("nama_pilihan").toString()
                val idPilihan = data.getStringExtra("id_pilihan").toString()

                when (opsi) {
                    "alasan_pindah" -> {
                        binding.edtAlasanPindah.setText(pilihan)
                        idAlasan = idPilihan
                    }

                    "jenis_kepindahan" -> {
                        binding.edtJenisKepindahan.setText(pilihan)
                        idJenisPindah = idPilihan
                    }
                    "status_pindah_kk" -> {

                        when (statusField) {
                            "kk_pindah" -> {
                                binding.edtStatusKkPindah.setText(pilihan)
                                idStatusKkPindah = idPilihan
                            }
                            "kk_tidak_pindah" -> {
                                binding.edtStatusKkTidakPindah.setText(pilihan)
                                idStatusKkTidakPindah = idPilihan
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DataAlasanSuratPindahFragment()
    }

}