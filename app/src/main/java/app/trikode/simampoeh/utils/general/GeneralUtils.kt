package app.trikode.simampoeh.utils.general

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import app.trikode.simampoeh.ui.utils.option.OptionMenuActivity
import app.trikode.simampoeh.ui.utils.option.OptionWilayahActivity

object GeneralUtils {

    const val FIELD_REQUIRED = "Harus diisi"
    const val WRONG_FORMAT = "Format salah"

    //email checker
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //string length checker
    fun checkStringLength(str: String, expectedLength: Int): Boolean {
        return str.length == expectedLength
    }

    fun activityResultLauncher(
        context: Context,
        menuOption: String,
        parent: String?,
        title: String,
        resultLauncher: ActivityResultLauncher<Intent>
    ) {
        val mOptionIntent = Intent(context, OptionMenuActivity::class.java)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_OPTION, menuOption)
        mOptionIntent.putExtra(OptionMenuActivity.MENU_PARENT, parent)
        mOptionIntent.putExtra(OptionMenuActivity.TITLE, title)
        resultLauncher.launch(mOptionIntent)
    }

    fun wilayahActivityResultLauncher(
        context: Context,
        title: String,
        request: String?,
        provinsi: String? = null,
        kabupaten: String? = null,
        kecamatan: String? = null,
        resultLauncher: ActivityResultLauncher<Intent>
    ) {
        val mOptionIntent = Intent(context, OptionWilayahActivity::class.java)
        mOptionIntent.putExtra(OptionWilayahActivity.REQUEST, request)
        mOptionIntent.putExtra(OptionWilayahActivity.ID_PROVINSI, provinsi)
        mOptionIntent.putExtra(OptionWilayahActivity.ID_KABUPATEN, kabupaten)
        mOptionIntent.putExtra(OptionWilayahActivity.ID_KECAMATAN, kecamatan)
        mOptionIntent.putExtra(OptionWilayahActivity.TITLE, title)
        resultLauncher.launch(mOptionIntent)
    }

}