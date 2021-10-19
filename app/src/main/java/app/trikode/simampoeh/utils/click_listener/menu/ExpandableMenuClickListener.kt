package app.trikode.simampoeh.utils.click_listener.menu

import android.view.View
import app.trikode.simampoeh.domain.model.menu.Menu

interface ExpandableMenuClickListener {

    fun onMenuGroupClick(view: View, menu: Menu)

    fun onMenuChildClick(view: View, menu: Menu)

}