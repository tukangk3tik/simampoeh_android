package app.trikode.simampoeh.utils.click_listener.menu

import android.view.View
import app.trikode.simampoeh.domain.model.menu.Menu

interface MenuClickListener {

    fun onMenuItemClicked(view: View, menu: Menu)

}