package app.trikode.simampoeh.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.trikode.simampoeh.databinding.ItemListMenuAllBinding
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.utils.click_listener.menu.MenuClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MenuAllListAdapter : RecyclerView.Adapter<MenuAllListAdapter.ViewHolder>() {

    private val listMenu = ArrayList<Menu>()
    var listener: MenuClickListener? = null

    inner class ViewHolder(private val binding: ItemListMenuAllBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            with(binding) {
                
                Glide.with(itemView.context)
                    .load(menu.imageIcon)
                    .apply(RequestOptions().override(350, 550))
                    .into(menuIcon)

                namaMenu.text = itemView.resources.getString(menu.nameStrRes)

                itemView.setOnClickListener {
                    listener?.onMenuItemClicked(itemView, menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListMenuAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
    }

    override fun getItemCount(): Int = listMenu.size

    fun setMenu(homeMenu: List<Menu>){
        listMenu.clear()
        listMenu.addAll(homeMenu)
    }
}