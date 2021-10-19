package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.R
import app.trikode.simampoeh.databinding.ItemListUploadBerkasBinding
import app.trikode.simampoeh.domain.model.upload_berkas.BerkasResponse
import app.trikode.simampoeh.utils.click_listener.upload_berkas.UploadBerkasClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UploadBerkasListAdapter : RecyclerView.Adapter<UploadBerkasListAdapter.ViewHolder>() {

    private val listBerkas = ArrayList<BerkasResponse>()
    var listener: UploadBerkasClickListener? = null

    inner class ViewHolder(private val binding: ItemListUploadBerkasBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(berkas: BerkasResponse, position: Int) {

            with(binding) {
                var textWajib = "Opsional"
                if (berkas.isRequired == "Y")  {
                    textWajib = "Wajib"
                    wajib.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.danger))
                } else {
                    wajib.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.info))
                }

                if (berkas.urlGambar != null){
                    Glide.with(itemView.context)
                        .load(berkas.urlGambar)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_img_error)
                        .into(imageUploaded)

                    imageUploaded.visibility = View.VISIBLE
                    imagePlaceholder.visibility = View.GONE
                }
                namaBerkas.text = berkas.nama
                wajib.text = textWajib

                itemView.setOnClickListener {
                    listener?.onBerkasItemClicked(itemView, berkas, position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUploadBerkasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBerkas[position], position)
    }

    override fun getItemCount(): Int = listBerkas.size

    fun setBerkas(berkas: List<BerkasResponse>){
        listBerkas.clear()
        listBerkas.addAll(berkas)
    }

    fun dataChange(position: Int){
        notifyItemChanged(position)
    }
}