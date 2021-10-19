package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListOptionMenuBinding
import app.trikode.simampoeh.domain.model.option.OptionMenu
import app.trikode.simampoeh.utils.click_listener.menu.OptionMenuClickListener

class OptionMenuListAdapter : RecyclerView.Adapter<OptionMenuListAdapter.ViewHolder>() {

    private val listOptionMenu = ArrayList<OptionMenu>()
    var listener: OptionMenuClickListener? = null

    inner class ViewHolder(private val binding: ItemListOptionMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(optionMenu: OptionMenu) {
            with(binding) {

                namaOpsi.text = optionMenu.namaOpsi

                itemView.setOnClickListener {
                    listener?.onOptionItemClicked(itemView, optionMenu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListOptionMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOptionMenu[position])
    }

    override fun getItemCount(): Int = listOptionMenu.size

    fun setList(options: List<OptionMenu>){
        listOptionMenu.clear()
        listOptionMenu.addAll(options)
    }
}