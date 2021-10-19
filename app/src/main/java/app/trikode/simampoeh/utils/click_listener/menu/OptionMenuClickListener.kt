package app.trikode.simampoeh.utils.click_listener.menu

import android.view.View
import app.trikode.simampoeh.domain.model.option.OptionMenu

interface OptionMenuClickListener {

    fun onOptionItemClicked(view: View, optionMenu: OptionMenu)

}