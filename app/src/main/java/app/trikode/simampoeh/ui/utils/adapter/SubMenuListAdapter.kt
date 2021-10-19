package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListSubmenuBinding
import app.trikode.simampoeh.domain.model.menu.Menu

class SubMenuListAdapter : RecyclerView.Adapter<SubMenuListAdapter.ViewHolder>() {

    private val listSubMenu = ArrayList<Menu>()
    //var listener: MenuClickListener? = null

    inner class ViewHolder(private val binding: ItemListSubmenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            with(binding) {
                namaMenu.text = itemView.resources.getString(menu.nameStrRes)

                itemView.setOnClickListener {
                    //listener?.onMenuItemClicked(itemView, menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListSubmenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSubMenu[position])
    }

    override fun getItemCount(): Int = listSubMenu.size

    fun setSubMenu(subMenu: List<Menu>){
        listSubMenu.clear()
        listSubMenu.addAll(subMenu)
    }
}