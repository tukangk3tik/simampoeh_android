package app.trikode.simampoeh.utils.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    fun showMessageOKCancel(
        context: Context,
        title: String,
        message: String,
        okListener: (Any, Any) -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

}