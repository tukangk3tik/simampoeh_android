package app.trikode.simampoeh.ui.pengajuan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan.PengajuanResponse
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentPengajuanBinding
import app.trikode.simampoeh.ui.pengajuan.detail.PengajuanDetailActivity
import app.trikode.simampoeh.ui.utils.adapter.PengajuanListAdapter
import app.trikode.simampoeh.utils.click_listener.pengajuan.PengajuanListClickListener
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse

class PengajuanFragment : Fragment(), PengajuanListClickListener {

    private lateinit var pengajuanViewModel: PengajuanViewModel
    private var _binding: FragmentPengajuanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPengajuanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        pengajuanViewModel = ViewModelProvider(requireActivity(), factory).get(PengajuanViewModel::class.java)

        val adapter = PengajuanListAdapter()

        adapter.listener = this
        binding.rvPengajuan.layoutManager = LinearLayoutManager(view.context)
        binding.rvPengajuan.adapter = adapter

        isLoading(true)
        pengajuanViewModel.setListPengajuan()
        pengajuanViewModel.listPengajuan.observe(viewLifecycleOwner, { response ->
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
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    isLoading(false)
                    isEmpty(true)
                }
                is ApiResponse.Loading -> isLoading(true)
            }
        })

        binding.srPengajuan.setOnRefreshListener {
            pengajuanViewModel.setListPengajuan()
        }
    }

    override fun onItemClicked(view: View, pengajuanResponse: PengajuanResponse) {
        val mIntent = Intent(requireContext(), PengajuanDetailActivity::class.java)
        mIntent.putExtra(PengajuanDetailActivity.UID_PENGAJUAN, pengajuanResponse.uid)
        startActivity(mIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isEmpty(state: Boolean) {
        if (state) {
            binding.rvPengajuan.visibility = View.GONE
            binding.viewPengajuanKosong.visibility = View.VISIBLE
        } else {
            binding.rvPengajuan.visibility = View.VISIBLE
            binding.viewPengajuanKosong.visibility = View.GONE
        }
    }

    fun isLoading(state: Boolean) {
        if (state) {
            binding.srPengajuan.isRefreshing = true
            //binding.progressBar.visibility = View.VISIBLE
            binding.rvPengajuan.visibility = View.GONE
            binding.viewPengajuanKosong.visibility = View.GONE
        } else {
            binding.srPengajuan.isRefreshing = false
        }
    }

}