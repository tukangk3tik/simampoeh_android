package app.trikode.simampoeh.ui.menu_lainnya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.ActivityMenuLainnyaBinding
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.ui.layanan_syarat.LayananSyaratActivity
import app.trikode.simampoeh.ui.utils.adapter.MenuAllListAdapter
import app.trikode.simampoeh.utils.click_listener.menu.MenuClickListener
import app.trikode.simampoeh.utils.routing.LayananRoutes
import app.trikode.simampoeh.utils.session.SessionHelper
import kotlinx.android.synthetic.main.item_list_pengajuan.*

class MenuLainnyaActivity : AppCompatActivity(), MenuClickListener {

    private lateinit var binding: ActivityMenuLainnyaBinding
    private val viewModel: MenuLainnyaViewModel by viewModels()

    private val appRoutes = LayananRoutes.getInstance()

    private var statusRouting = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusRouting = intent.getStringExtra(STATUS_ROUTING).toString()

        if (statusRouting != "") {

            binding = ActivityMenuLainnyaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if (statusRouting == "TRUE") {
                binding.tvTitle.text = resources.getString(R.string.semua_layanan)
            } else {
                binding.tvTitle.text = resources.getString(R.string.syarat_layanan)
            }

            val adapter = MenuAllListAdapter()
            adapter.listener = this
            binding.rvMenu.layoutManager = LinearLayoutManager(this)
            binding.rvMenu.adapter = adapter

            viewModel.listMenu.observe(this, {
                if (it.isNotEmpty()) {
                    adapter.setMenu(it)
                }
            })

            binding.btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onMenuItemClicked(view: View, menu: Menu) {
        if (statusRouting == "TRUE" && SessionHelper.checkLogin(this)) {
            appRoutes.setDestination(menu.route)
            val destination = appRoutes.getRoute()

            if (destination != null) {
                val mIntent = Intent(this, destination)
                startActivity(mIntent)
            }
        } else {
            val mIntent = Intent(this, LayananSyaratActivity::class.java)
            mIntent.putExtra(LayananSyaratActivity.LAYANAN, menu.urlString)
            startActivity(mIntent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        //for check when route or view syarat
        const val STATUS_ROUTING = "status_routing"
    }

}