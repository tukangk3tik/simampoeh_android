package app.trikode.simampoeh.ui.menu_lainnya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.databinding.ActivityMenuLainnyaBinding
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.ui.utils.adapter.MenuAllListAdapter
import app.trikode.simampoeh.utils.click_listener.menu.MenuClickListener
import app.trikode.simampoeh.utils.routing.LayananRoutes
import app.trikode.simampoeh.utils.session.SessionHelper

class MenuLainnyaActivity : AppCompatActivity(), MenuClickListener {

    private lateinit var binding: ActivityMenuLainnyaBinding
    private val viewModel: MenuLainnyaViewModel by viewModels()

    private val appRoutes = LayananRoutes.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuLainnyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MenuAllListAdapter()
        adapter.listener = this
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = adapter

        viewModel.listMenu.observe(this, {
            if (it.isNotEmpty()) {
                adapter.setMenu(it)
            }
        })
    }

    override fun onMenuItemClicked(view: View, menu: Menu) {
        goToRoute(menu)
    }

    private fun goToRoute(menu: Menu){
        if (SessionHelper.checkLogin(this)) {
            appRoutes.setDestination(menu.route)
            val destination = appRoutes.getRoute()

            if (destination != null) {
                val mIntent = Intent(binding.root.context, destination)
                startActivity(mIntent)
            }
        }
    }

}