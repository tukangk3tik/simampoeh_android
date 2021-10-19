package app.trikode.simampoeh.utils.session

import android.content.Context
import androidx.core.content.edit
import app.trikode.simampoeh.domain.model.user.User

class SessionManager (val context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_session"
        private const val TOKEN = "token"
        private const val UID = "uid"
        private const val NIK = "nik"
        private const val NAMA = "nama"
        private const val EMAIL = "email"
        private const val PROVINSI = "provinsi"
        private const val ID_PROVINSI = "id_provinsi"
        private const val KABUPATEN = "kabupaten"
        private const val ID_KABUPATEN = "id_kabupaten"
        private const val KECAMATAN = "kecamatan"
        private const val ID_KECAMATAN = "id_kecamatan"
        private const val KELURAHAN = "kelurahan"
        private const val ID_KELURAHAN = "id_kelurahan"
        private const val ALAMAT = "alamat"
        private const val IS_LOGIN = "status_login"
    }

    fun createLoginSession(value: User) {
        preferences.edit {
            putString(UID, value.uid)
            putString(NIK, value.nik)
            putString(NAMA, value.nama)
            putString(EMAIL, value.email)
            putString(PROVINSI, value.provinsi)
            putString(ID_PROVINSI, value.idProvinsi)
            putString(KABUPATEN, value.kabupaten)
            putString(ID_KABUPATEN, value.idKabupaten)
            putString(KECAMATAN, value.kecamatan)
            putString(ID_KECAMATAN, value.idKecamatan)
            putString(KELURAHAN, value.kelurahan)
            putString(ID_KELURAHAN, value.idKelurahan)
            putString(ALAMAT, value.alamat)
            putBoolean(IS_LOGIN, true)
        }
    }

    fun getLoginSession(): User {
        val uid = preferences.getString(UID, "").toString()
        val nik = preferences.getString(NIK, "").toString()
        val nama = preferences.getString(NAMA, "").toString()
        val email = preferences.getString(EMAIL, "").toString()
        val provinsi = preferences.getString(PROVINSI, "").toString()
        val idProvinsi = preferences.getString(ID_PROVINSI, "").toString()
        val kabupaten = preferences.getString(KABUPATEN, "").toString()
        val idKabupaten = preferences.getString(ID_KABUPATEN, "").toString()
        val kecamatan = preferences.getString(KECAMATAN, "").toString()
        val idKecamatan = preferences.getString(ID_KECAMATAN, "").toString()
        val kelurahan = preferences.getString(KELURAHAN, "").toString()
        val idKelurahan = preferences.getString(ID_KELURAHAN, "").toString()
        val alamat = preferences.getString(ALAMAT, "").toString()

        return User(uid, nik, nama, email, provinsi, idProvinsi, kabupaten, idKabupaten,
        kecamatan, idKecamatan, kelurahan, idKelurahan, alamat)
    }


    fun updateToken(newToken: String?) {
        if (newToken != null){
            preferences.edit {
                putString(TOKEN, newToken)
            }
        }
    }

    fun getToken(): String {
        return preferences.getString(TOKEN, "").toString()
    }

    fun isLoggedIn(): Boolean = preferences.getBoolean(IS_LOGIN, false)

    fun logout() {
        preferences.edit{
            clear()
        }
    }
}