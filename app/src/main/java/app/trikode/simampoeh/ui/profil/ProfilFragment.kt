package app.trikode.simampoeh.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.databinding.FragmentProfilBinding
import app.trikode.simampoeh.ui.main_non_login.MainNonLoginActivity
import app.trikode.simampoeh.utils.dialog.DialogUtils
import app.trikode.simampoeh.utils.session.SessionHelper

class ProfilFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfilBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var profilViewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener(this)

        profilViewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)

        val userData = SessionHelper.getSession(view.context)
        binding.tvNama.text = userData.nama
        binding.tvNik.text = userData.nik
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnLogout.id -> {
                DialogUtils.showMessageOKCancel(binding.root.context, "Logout", "Anda ingin keluar?") { _, _ ->
                    SessionHelper.logout(binding.root.context)
                    val mIntent = Intent(binding.root.context, MainNonLoginActivity::class.java)
                    activity?.finish()
                    startActivity(mIntent)
                }
            }
        }
    }

}