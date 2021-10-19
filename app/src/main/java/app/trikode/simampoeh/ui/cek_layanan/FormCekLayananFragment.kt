package app.trikode.simampoeh.ui.cek_layanan

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.FragmentFormCekLayananBinding
import app.trikode.simampoeh.utils.general.GeneralUtils

class FormCekLayananFragment : Fragment() {

    private var _binding: FragmentFormCekLayananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFormCekLayananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCek.setOnClickListener {
            val nik = binding.edtNik.text.toString()
            val kode = binding.edtKode.text.toString()

            when {
                TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                !GeneralUtils.checkStringLength(nik, 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                TextUtils.isEmpty(kode) -> binding.edtKode.error = GeneralUtils.FIELD_REQUIRED
                else -> {
                    val mTujuanFragment = WebviewCekLayananFragment.newInstance()
                    val mBundle = Bundle()

                    mBundle.putString(WebviewCekLayananFragment.NIK, nik)
                    mBundle.putString(WebviewCekLayananFragment.KODE, kode)
                    mTujuanFragment.arguments = mBundle

                    val mFragmentManager = parentFragmentManager

                    mFragmentManager.commit {
                        replace(
                            R.id.container,
                            mTujuanFragment,
                            FormCekLayananFragment::class.java.simpleName
                        )
                        addToBackStack(null)
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
        fun newInstance() = FormCekLayananFragment()
    }
}