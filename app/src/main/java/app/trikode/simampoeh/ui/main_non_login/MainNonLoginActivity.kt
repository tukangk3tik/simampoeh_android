package app.trikode.simampoeh.ui.main_non_login

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.ActivityMainNonLoginBinding
import app.trikode.simampoeh.domain.model.informasi.Informasi
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.ui.cek_layanan.CekLayananActivity
import app.trikode.simampoeh.ui.layanan_syarat.LayananSyaratActivity
import app.trikode.simampoeh.ui.login.LoginActivity
import app.trikode.simampoeh.ui.menu_lainnya.MenuLainnyaActivity
import app.trikode.simampoeh.ui.utils.adapter.InformasiListAdapter
import app.trikode.simampoeh.ui.utils.adapter.MenuGridAdapter
import app.trikode.simampoeh.utils.click_listener.menu.InformasiClickListener
import app.trikode.simampoeh.utils.click_listener.menu.MenuClickListener
import app.trikode.simampoeh.utils.routing.LayananRoutes

class MainNonLoginActivity : AppCompatActivity(), View.OnClickListener, MenuClickListener, InformasiClickListener {

    private lateinit var binding: ActivityMainNonLoginBinding

    private val viewModel: MainNonLoginViewModel by viewModels()
    private val appRoutes = LayananRoutes.getInstance()
    private var doubleBackToExitPressedOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNonLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarDrawable = ColorDrawable(Color.parseColor("#ffffff"))
        supportActionBar?.setBackgroundDrawable(actionBarDrawable)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.logo_32x98)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val menuAdapter = MenuGridAdapter()
        menuAdapter.listener = this
        binding.rvMenu.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.rvMenu.adapter = menuAdapter

        viewModel.listMenu.observe(this, {
            if (it.isNotEmpty()){
                menuAdapter.setMenu(it)
            }
        })

        val informasiAdapter = InformasiListAdapter()
        informasiAdapter.listener = this
//        binding.rvInformasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvInformasi.adapter = informasiAdapter

        viewModel.listInformasi.observe(this, {
            if (it.isNotEmpty()){
                informasiAdapter.setInfo(it)
            }
        })

        binding.btnLogin.setOnClickListener(this)
        binding.btnCekLayanan.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnLogin.id -> {
                val mIntent = Intent(this, LoginActivity::class.java)
                finish()
                startActivity(mIntent)
            }
            binding.btnCekLayanan.id -> {
                val mIntent = Intent(this, CekLayananActivity::class.java)
                startActivity(mIntent)
            }
        }
    }

    override fun onMenuItemClicked(view: View, menu: Menu) {
        if (menu.id == 99) {
            appRoutes.setDestination(menu.route)
            val destination = appRoutes.getRoute()

            if (destination != null) {
                val mIntent = Intent(this, destination)
                mIntent.putExtra(MenuLainnyaActivity.STATUS_ROUTING, "FALSE")
                startActivity(mIntent)
            }
        } else {
            val mIntent = Intent(this, LayananSyaratActivity::class.java)
            mIntent.putExtra(LayananSyaratActivity.LAYANAN, menu.urlString)
            startActivity(mIntent)
        }
    }

    override fun onInformasiItemClicked(view: View, informasi: Informasi) {

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
        }

        if (!doubleBackToExitPressedOnce) Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        doubleBackToExitPressedOnce = true
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }

}