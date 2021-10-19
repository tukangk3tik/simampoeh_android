package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListAnggotaKeluargaBinding
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.utils.click_listener.AnggotaKeluargaClickListener

class AnggotaKeluargaListAdapter : RecyclerView.Adapter<AnggotaKeluargaListAdapter.ViewHolder>() {

    private val listAnggotaKel = ArrayList<AnggotaKeluargaResponse>()
    var listener: AnggotaKeluargaClickListener? = null

    inner class ViewHolder(private val binding: ItemListAnggotaKeluargaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(anggotaKeluarga: AnggotaKeluargaResponse) {
            with(binding) {

                nama.text = anggotaKeluarga.nama
                nik.text = anggotaKeluarga.nik
                shdk.text = anggotaKeluarga.namaShdk

                btnHapus.setOnClickListener {
                    listener?.onItemClicked(itemView, anggotaKeluarga)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAnggotaKeluargaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAnggotaKel[position])
    }

    override fun getItemCount(): Int = listAnggotaKel.size

    fun setList(listAnggotaKeluarga: List<AnggotaKeluargaResponse>){
        listAnggotaKel.clear()
        listAnggotaKel.addAll(listAnggotaKeluarga)
    }
}