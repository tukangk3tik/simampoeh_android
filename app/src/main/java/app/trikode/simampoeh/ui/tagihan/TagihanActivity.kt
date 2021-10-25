package app.trikode.simampoeh.ui.tagihan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityTagihanBinding
import app.trikode.simampoeh.domain.model.tagihan.Tagihan
import app.trikode.simampoeh.ui.utils.adapter.TagihanListAdapter
import app.trikode.simampoeh.utils.click_listener.tagihan.TagihanListClickListener
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse

class TagihanActivity : AppCompatActivity(), TagihanListClickListener {

    private lateinit var binding: ActivityTagihanBinding

    private lateinit var tagihanViewModel: TagihanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTagihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        tagihanViewModel = ViewModelProvider(this, factory)[TagihanViewModel::class.java]

        val adapter = TagihanListAdapter()
        adapter.listener = this
        binding.rvTagihan.layoutManager = LinearLayoutManager(this)
        binding.rvTagihan.adapter = adapter

        isLoading(true)
        tagihanViewModel.setListTagihan()
        tagihanViewModel.listTagihan.observe(this, { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        if (it.size > 0){
                            adapter.setList(it)
                            isEmpty(false)
                        } else {
                            isEmpty(true)
                        }
                    }
                    isLoading(false)
                }
                is ApiResponse.Error -> {
                    response.message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                    isLoading(false)
                    isEmpty(true)
                }
                is ApiResponse.Loading -> isLoading(true)
            }
        })

        binding.tagihanSwipeRefresh.setOnRefreshListener {
            isLoading(true)
            tagihanViewModel.setListTagihan()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onItemClicked(view: View, tagihan: Tagihan) {
        val mIntent = Intent(this, TagihanDetailActivity::class.java)
        mIntent.putExtra(TagihanDetailActivity.KODE_STS, tagihan.noSts)
        mIntent.putExtra(TagihanDetailActivity.TEMPO, tagihan.jatuhTempo)
        mIntent.putExtra(TagihanDetailActivity.LAYANAN, tagihan.namaJenis)
        startActivity(mIntent)
    }

    fun isEmpty(state: Boolean) {
        if (state) {
            binding.rvTagihan.visibility = View.GONE
            binding.viewPengajuanKosong.visibility = View.VISIBLE
        } else {
            binding.rvTagihan.visibility = View.VISIBLE
            binding.viewPengajuanKosong.visibility = View.GONE
        }
    }

    fun isLoading(state: Boolean) {
        if (state) {
            binding.tagihanSwipeRefresh.isRefreshing = true
            //binding.progressBar.visibility = View.VISIBLE
            binding.rvTagihan.visibility = View.GONE
            binding.viewPengajuanKosong.visibility = View.GONE
        } else {
            binding.tagihanSwipeRefresh.isRefreshing = false
        }
    }
}