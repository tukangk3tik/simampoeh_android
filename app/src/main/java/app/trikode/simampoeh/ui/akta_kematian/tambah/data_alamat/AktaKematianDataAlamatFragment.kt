package app.trikode.simampoeh.ui.akta_kematian.tambah.data_alamat

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
import app.trikode.simampoeh.databinding.FragmentAktaKematianDataAlamatBinding
import app.trikode.simampoeh.ui.akta_kematian.tambah.TambahAktaKematianViewModel
import app.trikode.simampoeh.ui.akta_kematian.tambah.data_kematian.AktaKematianDataKematianFragment
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.GeneralUtils

class AktaKematianDataAlamatFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAktaKematianDataAlamatBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaKematianViewModel: TambahAktaKematianViewModel by activityViewModels()

    private var idProvinsi = ""
    private var idKabupaten = ""
    private var idKecamatan = ""
    private var idKelurahan = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaKematianDataAlamatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.edtProvinsi.setOnClickListener(this)
        binding.edtKabupatenKota.setOnClickListener(this)
        binding.edtKecamatan.setOnClickListener(this)
        binding.edtKelurahan.setOnClickListener(this)
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
                val noHp = binding.edtNoHandphone.text.toString()
                val telepon = binding.edtTelepon.text.toString()


                tambahAktaKematianViewModel.dataPengajuan?.alamatMati = alamat
                tambahAktaKematianViewModel.dataPengajuan?.rt = rt
                tambahAktaKematianViewModel.dataPengajuan?.rw = rw
                tambahAktaKematianViewModel.dataPengajuan?.idProvinsiMati = "$idProvinsi|$provinsi"
                tambahAktaKematianViewModel.dataPengajuan?.idKabupatenMati = "$idKabupaten|$kabupaten"
                tambahAktaKematianViewModel.dataPengajuan?.idKecamatanMati = "$idKecamatan|$kecamatan"
                tambahAktaKematianViewModel.dataPengajuan?.idKelurahanMati = "$idKelurahan|$kelurahan"
                tambahAktaKematianViewModel.dataPengajuan?.kodePos = kodePos
                tambahAktaKematianViewModel.dataPengajuan?.noHandphone = noHp
                tambahAktaKematianViewModel.dataPengajuan?.telepon = telepon

                val mTujuanFragment = AktaKematianDataKematianFragment.newInstance()
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
                        AktaKematianDataAlamatFragment::class.java.simpleName
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

    companion object {
        @JvmStatic
        fun newInstance() = AktaKematianDataAlamatFragment()
    }


}