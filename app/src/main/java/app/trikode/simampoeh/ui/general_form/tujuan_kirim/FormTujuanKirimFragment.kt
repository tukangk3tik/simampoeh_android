package app.trikode.simampoeh.ui.general_form.tujuan_kirim

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
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.TujuanKirim
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentGeneralTujuanKirimBinding
import app.trikode.simampoeh.ui.general_form.upload_berkas.FormUploadBerkasFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.option.OptionMenuActivity
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper

/**
 *  This form using for all where needed
 *  Created by Felix Serang, 21 September 2021
 */
class FormTujuanKirimFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentGeneralTujuanKirimBinding? = null
    private val binding get() = _binding!!

    private val tujuanKirimViewModel: FormTujuanKirimViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var endFragment: Fragment

    private var idProvinsi: String = ""
    private var idKabupaten: String = ""
    private var idKecamatan: String = ""
    private var idKelurahan: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGeneralTujuanKirimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

        binding.edtKecamatan.setOnClickListener(this)
        binding.edtKelurahan.setOnClickListener(this)

        binding.kirimViaPos.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.formulirPos.visibility = View.VISIBLE
            } else {
                binding.formulirPos.visibility = View.GONE
            }
        }

        val dataPengiriman = tujuanKirimViewModel.getTujuanKirim()

        if (dataPengiriman.kirim != null) {
            when(dataPengiriman.kirim) {
                "Y" -> {
                    binding.kirimViaPos.isChecked = true
                    binding.formulirPos.visibility = View.VISIBLE

                    binding.edtAlamat.setText(dataPengiriman.alamat)
                    binding.edtKodepos.setText(dataPengiriman.kodePos)

                    if (dataPengiriman.idProvinsi != null){
                        val provinsi = dataPengiriman.idProvinsi.toString().split("|")
                        idProvinsi = provinsi[0]                    //index 0 for id
                        binding.edtProvinsi.setText(provinsi[1])   //index 1 for name
                    }

                    if (dataPengiriman.idKabupaten != null){
                        val kabupaten = dataPengiriman.idKabupaten.toString().split("|")
                        idKabupaten = kabupaten[0]
                        binding.edtKabupatenKota.setText(kabupaten[1])
                    }

                    if (dataPengiriman.idKecamatan != null){
                        val kecamatan = dataPengiriman.idKecamatan.toString().split("|")
                        idKecamatan = kecamatan[0]
                        binding.edtKecamatan.setText(kecamatan[1])
                    }

                    if (dataPengiriman.idKelurahan != null){
                        val kelurahan = dataPengiriman.idKelurahan.toString().split("|")
                        idKelurahan = kelurahan[0]
                        binding.edtKelurahan.setText(kelurahan[1])
                    }
                }
                "N" -> binding.formulirPos.visibility = View.GONE
            }
        }

        searchViewModel.searchNik(SessionHelper.getSession(requireContext()).nik)
        searchViewModel.pendudukData.observe(viewLifecycleOwner, {
            it.data?.let { penduduk ->
                if (dataPengiriman.kirim == null) {
                    idProvinsi = penduduk.provinsi.toString()
                    idKabupaten = penduduk.kabupaten.toString()
                    idKecamatan = penduduk.kecamatan.toString()
                    idKelurahan = penduduk.kelurahan.toString()

                    binding.edtProvinsi.setText(penduduk.namaProvinsi)
                    binding.edtKabupatenKota.setText(penduduk.namaKabupaten)
                    binding.edtKecamatan.setText(penduduk.namaKecamatan)
                    binding.edtKelurahan.setText(penduduk.namaKelurahan)
                    binding.edtAlamat.setText(penduduk.alamatKk)
                }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnNext.id -> {
                val mBerkasFragment = FormUploadBerkasFragment.newInstance()
                mBerkasFragment.setNextFragment(endFragment)
                val mFragmentManager = parentFragmentManager

                var kirim = "N"
                if (binding.kirimViaPos.isChecked) {
                    kirim = "Y"
                }

                val namaProvinsi = binding.edtProvinsi.text.toString()
                val namaKabupaten = binding.edtKabupatenKota.text.toString()
                val namaKecamatan = binding.edtKecamatan.text.toString()
                val namaKelurahan = binding.edtKelurahan.text.toString()

                val alamat = binding.edtAlamat.text.toString()
                val kodePos = binding.edtKodepos.text.toString()

                val dataKirim = TujuanKirim(
                    kirim,
                    "$idProvinsi|$namaProvinsi",
                    "$idKabupaten|$namaKabupaten",
                    "$idKecamatan|$namaKecamatan",
                    "$idKelurahan|$namaKelurahan",
                    alamat,
                    kodePos
                )

                tujuanKirimViewModel.setTujuanKirim(dataKirim)

                mFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    replace(
                        R.id.container,
                        mBerkasFragment,
                        FormTujuanKirimFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
            binding.btnBack.id -> parentFragmentManager.popBackStack()
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
                    }
                    "kabupaten" -> {
                        binding.edtKabupatenKota.setText(pilihan)
                    }
                    "kecamatan" -> {
                        binding.edtKecamatan.setText(pilihan)
                        idKecamatan = idPilihan

                        binding.edtKelurahan.setText(resources.getString(R.string.pilih_kelurahan))
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

    private fun showOptionMenu(menuOption: String, parent: String?, title: String) {
        val mOptionIntent = Intent(binding.root.context, OptionMenuActivity::class.java)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_OPTION, menuOption)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_PARENT, parent)
        mOptionIntent.putExtra(OptionMenuActivity.TITLE, title)
        resultLauncher.launch(mOptionIntent)
    }

    fun setEndFragment(nFragment: Fragment) {
        endFragment = nFragment
    }

    companion object {
       fun newInstance() = FormTujuanKirimFragment()
    }

}