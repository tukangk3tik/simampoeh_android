package app.trikode.simampoeh.ui.akta_lahir.tambah.konfirmasi

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentAktaLahirKonfirmasiBinding
import app.trikode.simampoeh.ui.akta_lahir.tambah.TambahAktaLahirViewModel
import app.trikode.simampoeh.ui.general_form.tujuan_kirim.FormTujuanKirimViewModel
import app.trikode.simampoeh.ui.utils.KirimLayananViewModel
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.EventLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.medancapilpelaporan.utils.general.HandlerLiveData
import com.google.gson.Gson

class AktaLahirKonfirmasiFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAktaLahirKonfirmasiBinding? = null
    private val binding get() = _binding!!

    private val tambahAktaLahirViewModel: TambahAktaLahirViewModel by activityViewModels()
    private val formTujuanKirimViewModel: FormTujuanKirimViewModel by activityViewModels()
    private lateinit var kirimViewModel: KirimLayananViewModel

    private var sendResult = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAktaLahirKonfirmasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        joinAllData()

        val factory = ViewModelFactory.getInstance(requireActivity())
        kirimViewModel = ViewModelProvider(requireActivity(), factory)[KirimLayananViewModel::class.java]

        kirimViewModel.submitLayananHandler.observe(viewLifecycleOwner, submitLayanan)

        binding.btnKirim.setOnClickListener(this)
        binding.btnKeHome.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnKirim.id -> {
                showLoading(true)
                val jsonString = Gson().toJson(tambahAktaLahirViewModel.dataPengajuan)
                d("JSON_STRING", jsonString)
                kirimViewModel.submitLayanan("tambah_aktalahir", jsonString)
            }
            binding.btnBack.id -> {
                if (sendResult == 0){
                    parentFragmentManager.popBackStack()
                }
            }
            binding.btnKeHome.id -> activity?.finish()
        }
    }

    private fun joinAllData() {
        tambahAktaLahirViewModel.dataPengajuan?.kirim = formTujuanKirimViewModel.getTujuanKirim().kirim
        tambahAktaLahirViewModel.dataPengajuan?.idProvinsi = formTujuanKirimViewModel.getTujuanKirim().idProvinsi
        tambahAktaLahirViewModel.dataPengajuan?.idKabupaten = formTujuanKirimViewModel.getTujuanKirim().idKabupaten
        tambahAktaLahirViewModel.dataPengajuan?.idKecamatan = formTujuanKirimViewModel.getTujuanKirim().idKecamatan
        tambahAktaLahirViewModel.dataPengajuan?.idKelurahan = formTujuanKirimViewModel.getTujuanKirim().idKelurahan
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.btnKirim.visibility = View.GONE
            binding.btnKeHome.visibility = View.GONE
            binding.btnBack.visibility = View.GONE
            binding.progressKirim.visibility = View.VISIBLE
            binding.tvKonfirmasi.text = resources.getString(R.string.sedang_mengirim)
        } else {
            binding.progressKirim.visibility = View.GONE
        }
    }

    private val submitLayanan = Observer<EventLiveData<HandlerLiveData>> {
        it.getContentIfNotHandled().let { handler ->

            if (handler != null) {
                sendResult = handler.responseResult

                if (handler.responseResult > 0) {
                    Glide.with(requireContext())
                        .load(R.drawable.img_konfirmasi)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_img_error)
                        .into(binding.imageWarning)

                    binding.btnKeHome.visibility = View.VISIBLE
                    binding.tvKonfirmasi.text = handler.responseMessage
                } else {
                    binding.btnKirim.visibility = View.VISIBLE
                    binding.tvKonfirmasi.text = resources.getString(R.string.kirim_warning)
                    binding.btnBack.visibility = View.VISIBLE
                    AlertDialogHelper.showAlertDialog(handler.responseMessage, requireActivity())
                }
            }

            showLoading(false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AktaLahirKonfirmasiFragment()
    }

}