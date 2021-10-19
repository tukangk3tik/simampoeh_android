package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.ItemListInformasiBinding
import app.trikode.simampoeh.domain.model.informasi.Informasi
import app.trikode.simampoeh.utils.click_listener.menu.InformasiClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class InformasiListAdapter : RecyclerView.Adapter<InformasiListAdapter.ViewHolder>() {

    private val listInformasi = ArrayList<Informasi>()
    var listener: InformasiClickListener? = null

    inner class ViewHolder(private val binding: ItemListInformasiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(informasi: Informasi) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(informasi.urlPoster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_img_error)
                    .centerCrop()
                    .into(posterInformasi)

                itemView.setOnClickListener {
                    listener?.onInformasiItemClicked(itemView, informasi)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListInformasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listInformasi[position])
    }

    override fun getItemCount(): Int = listInformasi.size

    fun setInfo(homeMenu: List<Informasi>){
        listInformasi.clear()
        listInformasi.addAll(homeMenu)
    }
}