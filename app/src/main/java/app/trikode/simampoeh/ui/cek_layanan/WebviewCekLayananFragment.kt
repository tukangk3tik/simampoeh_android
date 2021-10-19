package app.trikode.simampoeh.ui.cek_layanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.trikode.simampoeh.databinding.FragmentWebviewCekLayananBinding
import app.trikode.simampoeh.utils.general.StringConfig
import java.util.*

class WebviewCekLayananFragment : Fragment() {

    private var _binding: FragmentWebviewCekLayananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebviewCekLayananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nik = arguments?.getString(NIK)
        val kode = arguments?.getString(KODE)?.uppercase(Locale.getDefault())

        if (nik != null && kode != null){
            val url = "${StringConfig.STAGING_URL}webview_mobile/informasi-layanan-$nik-${kode}"
            binding.wvCekLayanan.loadUrl(url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NIK = "nik"
        const val KODE = "kode"

        @JvmStatic
        fun newInstance() = WebviewCekLayananFragment()
    }
}