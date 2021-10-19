package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan.PengajuanResponse
import app.trikode.simampoeh.databinding.ItemListPengajuanBinding
import app.trikode.simampoeh.utils.click_listener.pengajuan.PengajuanListClickListener

class PengajuanListAdapter : RecyclerView.Adapter<PengajuanListAdapter.ViewHolder>() {

    private val listPengajuan = ArrayList<PengajuanResponse>()
    var listener: PengajuanListClickListener? = null

    inner class ViewHolder(private val binding: ItemListPengajuanBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pengajuan: PengajuanResponse) {
            with(binding) {

                jenisBerkas.text = pengajuan.namaPelayanan
                waktuPengajuan.text = pengajuan.waktuInput
                status.text = pengajuan.namaStatus

//                when (pengajuan.idStatus){
//                    2 -> status.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.warning))
//                }

                itemView.setOnClickListener {
                    listener?.onItemClicked(itemView, pengajuan)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPengajuan[position])
    }

    override fun getItemCount(): Int = listPengajuan.size

    fun setList(pengajuan: List<PengajuanResponse>){
        listPengajuan.clear()
        listPengajuan.addAll(pengajuan)
    }
}