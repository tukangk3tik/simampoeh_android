package app.trikode.simampoeh.ui.kk.tambah.form.data_anggota

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.FragmentDataAnggotaKkBinding
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.kk.tambah.TambahKkViewModel
import app.trikode.simampoeh.ui.kk.tambah.form.data_anggota.tambah.TambahAnggotaKkActivity
import app.trikode.simampoeh.ui.kk.tambah.konfirmasi.KkKonfirmasiFragment
import app.trikode.simampoeh.ui.utils.adapter.AnggotaKeluargaListAdapter
import app.trikode.simampoeh.utils.click_listener.AnggotaKeluargaClickListener
import app.trikode.simampoeh.utils.general.AlertDialogHelper

class DataAnggotaKkFragment : Fragment(), View.OnClickListener, AnggotaKeluargaClickListener {

    private var _binding: FragmentDataAnggotaKkBinding? = null
    private val binding get() = _binding!!

    private val formViewModel: TambahKkViewModel by activityViewModels()
    private val adapter = AnggotaKeluargaListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDataAnggotaKkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.btnTambah.setOnClickListener(this)

        adapter.listener = this
        binding.rvKeluarga.layoutManager = LinearLayoutManager(view.context)
        binding.rvKeluarga.adapter = adapter

        populateList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                mTujuanFragment.setEndFragment(KkKonfirmasiFragment.newInstance())

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
                        DataAnggotaKkFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
            binding.btnTambah.id -> {
                val mIntent = Intent(requireContext(), TambahAnggotaKkActivity::class.java)
                resultLauncher.launch(mIntent)
            }
        }
    }

    override fun onItemClicked(view: View, anggotaKeluarga: AnggotaKeluargaResponse) {
        showAlertDelete(anggotaKeluarga)
    }

    private fun showAlertDelete(anggotaKeluarga: AnggotaKeluargaResponse){
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alert.setTitle("Warning")
            .setMessage("Yakin anggota keluarga?")
            .setPositiveButton("Hapus") { dialog, _ ->
                formViewModel.removeAnggotaKeluarga(anggotaKeluarga)
                populateList()
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        alert.show()
    }

    private fun populateList() {
        formViewModel.getAnggotaKeluarga().let {
            if (it.size > 0) {
                adapter.setList(it)
                binding.rvKeluarga.visibility = View.VISIBLE
                binding.listKosong.visibility = View.GONE
            }
            else {
                binding.rvKeluarga.visibility = View.GONE
                binding.listKosong.visibility = View.VISIBLE
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){

                val nik = data.getStringExtra("nik").toString()
                d("RESULT_NIK", nik)

                if (nik == formViewModel.dataPemohon?.nik) {
                    AlertDialogHelper.showAlertDialog("NIK sama dengan pemohon, tidak bisa ditambahkan", requireContext())
                } else {
                    val nama = data.getStringExtra("nama").toString()
                    val idShdk = data.getStringExtra("idShdk").toString()
                    val namaShdk = data.getStringExtra("namaShdk").toString()

                    val dataAnggota = AnggotaKeluargaResponse(nik, nama, idShdk, namaShdk)
                    formViewModel.addAnggotaKeluarga(dataAnggota)

                    populateList()
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DataAnggotaKkFragment()
    }
}