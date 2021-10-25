package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListTagihanBinding
import app.trikode.simampoeh.domain.model.tagihan.Tagihan
import app.trikode.simampoeh.utils.click_listener.tagihan.TagihanListClickListener

class TagihanListAdapter : RecyclerView.Adapter<TagihanListAdapter.ViewHolder>() {

    private val listTagihan = ArrayList<Tagihan>()
    var listener: TagihanListClickListener? = null

    inner class ViewHolder(private val binding: ItemListTagihanBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tagihan: Tagihan) {
            with(binding) {

                nomorSts.text = tagihan.noSts
                jatuhTempo.text = tagihan.jatuhTempo
                layanan.text = tagihan.namaJenis

                itemView.setOnClickListener {
                    listener?.onItemClicked(itemView, tagihan)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListTagihanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTagihan[position])
    }

    override fun getItemCount(): Int = listTagihan.size

    fun setList(pengajuan: List<Tagihan>){
        listTagihan.clear()
        listTagihan.addAll(pengajuan)
    }
}