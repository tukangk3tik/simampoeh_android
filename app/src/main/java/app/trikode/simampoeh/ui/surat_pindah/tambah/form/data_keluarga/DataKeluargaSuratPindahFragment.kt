package app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentSuratPindahDataKeluargaBinding
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimFragment
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahViewModel
import app.trikode.simampoeh.ui.surat_pindah.tambah.konfirmasi.SuratPindahKonfirmasiFragment
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.utils.click_listener.AnggotaKeluargaCheckListener
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse

class DataKeluargaSuratPindahFragment : Fragment(), View.OnClickListener, AnggotaKeluargaCheckListener {

    private var _binding: FragmentSuratPindahDataKeluargaBinding? = null
    private val binding get() = _binding!!

    private val tambahSuratPindahViewModel: TambahSuratPindahViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuratPindahDataKeluargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        searchViewModel = ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        val adapter = DataKeluargaListAdapter()
        adapter.listener = this
        binding.rvKeluarga.layoutManager = LinearLayoutManager(view.context)
        binding.rvKeluarga.adapter = adapter

        searchViewModel.kartuKeluargaData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        if (it.anggota.size > 0) {
                            val checkedAnggota = tambahSuratPindahViewModel.dataAnggota

                            adapter.setKeluarga(it.anggota, checkedAnggota)
                            isDataKosong(false)
                        } else {
                            isDataKosong(true)
                        }
                    }
                }
                is ApiResponse.Error -> {
                    response.message?.let {
                        Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
                    }
                    isDataKosong(true)
                }
                is ApiResponse.Loading -> { }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val mTujuanFragment = FormTujuanKirimFragment.newInstance()
                mTujuanFragment.setEndFragment(SuratPindahKonfirmasiFragment.newInstance())

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
                        DataKeluargaSuratPindahFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onItemChecked(
        view: View,
        anggotaKeluarga: AnggotaKeluargaResponse,
        isChecked: Boolean,
    ) {
        if (isChecked) {
            tambahSuratPindahViewModel.dataAnggota.add(anggotaKeluarga)
        } else {
            tambahSuratPindahViewModel.dataAnggota.remove(anggotaKeluarga)
        }
    }

    private fun isDataKosong(state: Boolean) {
        if (state) {
            binding.rvKeluarga.visibility = View.GONE
            binding.nbCentang.visibility = View.GONE
            binding.dataKosong.visibility = View.VISIBLE
        } else {
            binding.rvKeluarga.visibility = View.VISIBLE
            binding.nbCentang.visibility = View.VISIBLE
            binding.dataKosong.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DataKeluargaSuratPindahFragment()
    }

}