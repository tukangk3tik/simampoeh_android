package app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_pindah

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.FragmentSuratPindahDataPindahBinding
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahViewModel
import app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_alasan_pindah.DataAlasanSuratPindahFragment
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.GeneralUtils

class DataPindahSuratPindahFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSuratPindahDataPindahBinding? = null
    private val binding get() = _binding!!

    private val tambahSuratPindahViewModel: TambahSuratPindahViewModel by activityViewModels()

    private var idProvinsi = ""
    private var idKabupaten = ""
    private var idKecamatan = ""
    private var idKelurahan = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentSuratPindahDataPindahBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.edtProvinsi.setOnClickListener(this)
        binding.edtKabupatenKota.setOnClickListener(this)
        binding.edtKecamatan.setOnClickListener(this)
        binding.edtKelurahan.setOnClickListener(this)

        val dataPengajuan = tambahSuratPindahViewModel.dataPengajuan

        //set if exist
        if (dataPengajuan != null) {
            binding.edtAlamat.setText(dataPengajuan.alamatPindah)
            binding.edtRt.setText(dataPengajuan.rtTujuan)
            binding.edtRw.setText(dataPengajuan.rwTujuan)
            binding.edtKodepos.setText(dataPengajuan.kodeposTujuan)
            binding.edtTelepon.setText(dataPengajuan.telepon)

            if (dataPengajuan.idProvinsiPindah != null){
                val provinsi = dataPengajuan.idProvinsiPindah.toString().split("|")
                idProvinsi = provinsi[0]                    //index 0 for id
                binding.edtProvinsi.setText(provinsi[1])   //index 1 for name
            }

            if (dataPengajuan.idKabupatenPindah != null){
                val kabupaten = dataPengajuan.idKabupatenPindah.toString().split("|")
                idKabupaten = kabupaten[0]
                binding.edtKabupatenKota.setText(kabupaten[1])
            }

            if (dataPengajuan.idKecamatanPindah != null){
                val kecamatan = dataPengajuan.idKecamatanPindah.toString().split("|")
                idKecamatan = kecamatan[0]
                binding.edtKecamatan.setText(kecamatan[1])
            }

            if (dataPengajuan.idKelurahanPindah != null){
                val kelurahan = dataPengajuan.idKelurahanPindah.toString().split("|")
                idKelurahan = kelurahan[0]
                binding.edtKelurahan.setText(kelurahan[1])
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.edtProvinsi.id -> {
                GeneralUtils.wilayahActivityResultLauncher(
                    binding.root.context, "Pilih Provinsi", "provinsi",
                    resultLauncher = resultLauncher)
            }
            binding.edtKabupatenKota.id -> {
                if (idProvinsi == "") {
                    AlertDialogHelper.showAlertDialog("Pilih Provinsi lebih dulu", requireContext())
                } else {
                    GeneralUtils.wilayahActivityResultLauncher(
                        binding.root.context, "Pilih Kabupaten/Kota", "kabupaten",
                        idProvinsi, resultLauncher = resultLauncher)
                }
            }
            binding.edtKecamatan.id -> {
                if (idKabupaten == "") {
                    AlertDialogHelper.showAlertDialog("Pilih Kabupaten/Kota lebih dulu", requireContext())
                } else {
                    GeneralUtils.wilayahActivityResultLauncher(
                        binding.root.context, "Pilih Kecamatan", "kecamatan",
                        idProvinsi, idKabupaten, resultLauncher = resultLauncher)
                }
            }
            binding.edtKelurahan.id -> {
                if (idKecamatan == "") {
                    AlertDialogHelper.showAlertDialog("Pilih Kecamatan lebih dulu", requireContext())
                } else {
                    GeneralUtils.wilayahActivityResultLauncher(
                        binding.root.context, "Pilih Kelurahan", "kelurahan",
                        idProvinsi, idKabupaten, idKecamatan, resultLauncher)
                }
            }
            binding.btnNext.id -> {
                val alamat = binding.edtAlamat.text.toString()
                val rt = binding.edtRt.text.toString()
                val rw = binding.edtRw.text.toString()
                val provinsi = binding.edtProvinsi.text.toString()
                val kabupaten = binding.edtKabupatenKota.text.toString()
                val kecamatan = binding.edtKecamatan.text.toString()
                val kelurahan = binding.edtKelurahan.text.toString()
                val kodePos = binding.edtKodepos.text.toString()
                val telepon = binding.edtTelepon.text.toString()

                tambahSuratPindahViewModel.dataPengajuan?.alamatPindah = alamat
                tambahSuratPindahViewModel.dataPengajuan?.rtTujuan = rt
                tambahSuratPindahViewModel.dataPengajuan?.rwTujuan = rw
                tambahSuratPindahViewModel.dataPengajuan?.kodeposTujuan = kodePos
                tambahSuratPindahViewModel.dataPengajuan?.telepon = telepon
                if (idProvinsi != "") tambahSuratPindahViewModel.dataPengajuan?.idProvinsiPindah = "$idProvinsi|$provinsi"
                if (idKabupaten != "") tambahSuratPindahViewModel.dataPengajuan?.idKabupatenPindah = "$idKabupaten|$kabupaten"
                if (idKecamatan != "") tambahSuratPindahViewModel.dataPengajuan?.idKecamatanPindah = "$idKecamatan|$kecamatan"
                if (idKelurahan != "") tambahSuratPindahViewModel.dataPengajuan?.idKelurahanPindah = "$idKelurahan|$kelurahan"

                val mTujuanFragment = DataAlasanSuratPindahFragment.newInstance()
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
                    "provinsi" -> {
                        binding.edtProvinsi.setText(pilihan)
                        idProvinsi = idPilihan

                        binding.edtKabupatenKota.setText(null)
                        idKabupaten = ""

                        binding.edtKecamatan.setText(null)
                        idKecamatan = ""

                        binding.edtKelurahan.setText(null)
                        idKelurahan = ""
                    }
                    "kabupaten" -> {
                        binding.edtKabupatenKota.setText(pilihan)
                        idKabupaten = idPilihan

                        binding.edtKecamatan.setText(null)
                        idKecamatan = ""

                        binding.edtKelurahan.setText(null)
                        idKelurahan = ""
                    }
                    "kecamatan" -> {
                        binding.edtKecamatan.setText(pilihan)
                        idKecamatan = idPilihan

                        binding.edtKelurahan.setText(null)
                        idKelurahan = ""
                    }
                    "kelurahan" -> {
                        binding.edtKelurahan.setText(pilihan)
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
        fun newInstance() = DataPindahSuratPindahFragment()
    }
}