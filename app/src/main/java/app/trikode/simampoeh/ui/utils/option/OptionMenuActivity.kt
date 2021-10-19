package app.trikode.simampoeh.ui.utils.option

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityOptionMenuBinding
import app.trikode.simampoeh.domain.model.option.OptionMenu
import app.trikode.simampoeh.ui.utils.adapter.OptionMenuListAdapter
import app.trikode.simampoeh.utils.click_listener.menu.OptionMenuClickListener
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse

class OptionMenuActivity : AppCompatActivity(), OptionMenuClickListener {

    private lateinit var binding: ActivityOptionMenuBinding
    private lateinit var optionMenuViewModel: OptionMenuViewModel
    private lateinit var adapter: OptionMenuListAdapter

    private var menuOption: String? = null

    companion object {
        const val MENU_OPTION = "menu_option"   //receiver request for, ex: Provinsi, Kabupaten
        const val MENU_PARENT = "menu_parent"
        const val TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        optionMenuViewModel = obtainViewModel(this)

        menuOption = intent.getStringExtra(MENU_OPTION).toString()
        val menuParent = intent.getStringExtra(MENU_PARENT).toString()

        binding.headerTitle.text = intent.getStringExtra(TITLE).toString()

        adapter = OptionMenuListAdapter()
        adapter.listener = this
        binding.rvOption.layoutManager = LinearLayoutManager(this)
        binding.rvOption.adapter = adapter

        if (menuOption != null) optionMenuViewModel.setOptionMenu(menuOption, menuParent)

        optionMenuViewModel.optionMenu.observe(this, { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        if (it.size > 0) {
                            adapter.setList(it)
                        }
                    }
                    showLoading(false)
                }
                is ApiResponse.Error -> {
                    response.message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                    showLoading(false)
                }
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
            }
        })

        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    override fun onOptionItemClicked(view: View, optionMenu: OptionMenu) {
        val mainIntent = Intent()
        mainIntent.putExtra("menu_option", menuOption)
        mainIntent.putExtra("nama_pilihan", optionMenu.namaOpsi)
        mainIntent.putExtra("id_pilihan", optionMenu.id)
        setResult(RESULT_OK, mainIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun obtainViewModel(activity: AppCompatActivity): OptionMenuViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(OptionMenuViewModel::class.java)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvOption.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvOption.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}