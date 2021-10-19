package app.trikode.simampoeh.ui.surat_pindah.tambah.form.data_keluarga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse
import app.trikode.simampoeh.databinding.ItemListKeluargaBinding
import app.trikode.simampoeh.utils.click_listener.AnggotaKeluargaCheckListener

class DataKeluargaListAdapter : RecyclerView.Adapter<DataKeluargaListAdapter.ViewHolder>() {

    private val listKeluarga = ArrayList<AnggotaKeluargaResponse>()
    private val checkedAnggota = ArrayList<AnggotaKeluargaResponse>()
    var listener: AnggotaKeluargaCheckListener? = null

    inner class ViewHolder(private val binding: ItemListKeluargaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(anggota: AnggotaKeluargaResponse, position: Int) {
            with(binding) {

                if (checkedAnggota.contains(anggota)) checkAnggota.isChecked = true

                nama.text = anggota.nama
                nik.text = anggota.nik

                checkAnggota.setOnCheckedChangeListener { compoundButton, b ->
                    var checked = true
                    if (!b) {
                        checked = false
                    }

                    listener?.onItemChecked(itemView, anggota, checked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListKeluargaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listKeluarga[position], position)
    }

    override fun getItemCount(): Int = listKeluarga.size

    fun setKeluarga(
        keluarga: List<AnggotaKeluargaResponse>,
        checkedAnggota: ArrayList<AnggotaKeluargaResponse>,
    ){
        listKeluarga.clear()
        checkedAnggota.addAll(checkedAnggota)
        listKeluarga.addAll(keluarga)
    }
}