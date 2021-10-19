package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListStatusPengajuanBinding
import app.trikode.simampoeh.domain.model.list_pengajuan.Pengajuan

class StatusPengajuanListAdapter : RecyclerView.Adapter<StatusPengajuanListAdapter.ViewHolder>() {

    private val listPengajuan = ArrayList<Pengajuan>()
    //var listener: MenuClickListener? = null

    inner class ViewHolder(private val binding: ItemListStatusPengajuanBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pengajuan: Pengajuan) {
            with(binding) {

                kodeLayanan.text = pengajuan.kode
                waktuPengajuan.text = pengajuan.waktuInput
                status.text = pengajuan.namaStatus

                when (pengajuan.idStatus){
                    2 -> status.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.warning))
                }

                itemView.setOnClickListener {
                    //listener?.onMenuItemClicked(itemView, menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListStatusPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPengajuan[position])
    }

    override fun getItemCount(): Int = listPengajuan.size

    fun setList(pengajuan: List<Pengajuan>){
        listPengajuan.clear()
        listPengajuan.addAll(pengajuan)
    }
}