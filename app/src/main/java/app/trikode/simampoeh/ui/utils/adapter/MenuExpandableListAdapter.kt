package app.trikode.simampoeh.ui.utils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import app.trikode.simampoeh.R
import app.trikode.simampoeh.domain.model.menu.Menu
import app.trikode.simampoeh.utils.click_listener.menu.ExpandableMenuClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MenuExpandableListAdapter
    internal constructor(
        private val context: Context
    ) : BaseExpandableListAdapter()
{

    private val menuList = ArrayList<Menu>()
    var listener: ExpandableMenuClickListener? = null

    fun setMenu(homeMenu: List<Menu>){
        menuList.clear()
        menuList.addAll(homeMenu)
    }

    override fun getGroupCount(): Int = menuList.size

    override fun getChildrenCount(groupPosition: Int): Int = menuList[groupPosition].child!!.size

    override fun getGroup(position: Int) : Any = menuList[position]

    override fun getChild(groupPosition: Int, childPosition: Int): Any = menuList[groupPosition].child!![childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val mConvertView = LayoutInflater.from(parent?.context).inflate(R.layout.item_list_menu_all, parent, false)
        val namaMenu = mConvertView.findViewById<AppCompatTextView>(R.id.nama_menu)
        val iconMenu = mConvertView.findViewById<AppCompatImageView>(R.id.menu_icon)

        namaMenu.text = context.getString(menuList[groupPosition].nameStrRes)

        Glide.with(context)
            .load(menuList[groupPosition].imageIcon)
            .apply(RequestOptions().override(350, 550))
            .into(iconMenu)

        if (menuList[groupPosition].child == null) {
            mConvertView.setOnClickListener {
                listener?.onMenuGroupClick(mConvertView, menuList[groupPosition])
            }
        }

        return mConvertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val mConvertView = LayoutInflater.from(parent?.context).inflate(R.layout.item_list_submenu, parent, false)

        if (menuList[groupPosition].child != null){
            val namaMenu = mConvertView.findViewById<AppCompatTextView>(R.id.nama_menu)
            namaMenu.text = context.getString(menuList[groupPosition].child!![childPosition].nameStrRes)

            mConvertView.setOnClickListener {
                listener?.onMenuChildClick(mConvertView, menuList[groupPosition].child!![childPosition])
            }
        }

        return mConvertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean  = true
}