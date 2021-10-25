package app.trikode.simampoeh.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.databinding.FragmentBerandaBinding
import app.trikode.simampoeh.domain.model.informasi.Informasi
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.ui.menu_lainnya.MenuLainnyaActivity
import app.trikode.simampoeh.ui.tagihan.TagihanActivity
import app.trikode.simampoeh.ui.utils.adapter.InformasiListAdapter
import app.trikode.simampoeh.ui.utils.adapter.MenuGridAdapter
import app.trikode.simampoeh.utils.click_listener.menu.InformasiClickListener
import app.trikode.simampoeh.utils.click_listener.menu.MenuClickListener
import app.trikode.simampoeh.utils.routing.LayananRoutes
import app.trikode.simampoeh.utils.session.SessionHelper

class BerandaFragment : Fragment(), View.OnClickListener, MenuClickListener {

    private lateinit var berandaViewModel: BerandaViewModel
    private var _binding: FragmentBerandaBinding? = null

    private val appRoutes = LayananRoutes.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        berandaViewModel = ViewModelProvider(this).get(BerandaViewModel::class.java)

        val menuAdapter = MenuGridAdapter()
        menuAdapter.listener = this
        binding.rvMenu.layoutManager = GridLayoutManager(view.context, 3, GridLayoutManager.VERTICAL, false)
        binding.rvMenu.adapter = menuAdapter

        berandaViewModel.listMenu.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                menuAdapter.setMenu(it)
            }
        })

       /* val informasiAdapter = InformasiListAdapter()
        informasiAdapter.listener = this
        binding.rvInformasi.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvInformasi.adapter = informasiAdapter

        berandaViewModel.listInformasi.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                informasiAdapter.setInfo(it)
            }
        })*/

        val userData = SessionHelper.getSession(view.context)
        binding.tvNamaUser.text = userData.nama
        binding.tvNik.text = userData.nik

        binding.btnTagihan.setOnClickListener(this)
        binding.btnSyarat.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClicked(view: View, menu: Menu) {
        appRoutes.setDestination(menu.route)
        val destination = appRoutes.getRoute()

        if (destination != null) {
            val mIntent = Intent(binding.root.context, destination)
            if (menu.id == 99) mIntent.putExtra(MenuLainnyaActivity.STATUS_ROUTING, "TRUE")
            startActivity(mIntent)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnTagihan.id -> {
                val mIntent = Intent(requireContext(), TagihanActivity::class.java)
                startActivity(mIntent)
            }
            binding.btnSyarat.id -> {
                appRoutes.setDestination("menu_lainnya")
                val destination = appRoutes.getRoute()

                if (destination != null) {
                    val mIntent = Intent(binding.root.context, destination)
                    mIntent.putExtra(MenuLainnyaActivity.STATUS_ROUTING, "FALSE")
                    startActivity(mIntent)
                }
            }
        }
    }
}