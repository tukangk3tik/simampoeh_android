package app.trikode.simampoeh.utils.session

import android.content.Context
import app.trikode.simampoeh.domain.model.user.User

object SessionHelper {

    private fun provideSession(context: Context): SessionManager {
        return SessionManager(context)
    }

    fun updateToken(token : String?, context: Context) {
        val userPreference = provideSession(context)
        userPreference.updateToken(token)
    }

    fun getToken(context: Context): String {
        val userPreference = provideSession(context)
        return userPreference.getToken()
    }

    fun checkLogin(context: Context) : Boolean {
        val userPreference = provideSession(context)
        return userPreference.isLoggedIn()
    }

    fun saveSession(user: User, context: Context) {
        val userPreference = provideSession(context)
        return userPreference.createLoginSession(user)
    }

    fun getSession(context: Context): User {
        val userPreference = provideSession(context)
        return userPreference.getLoginSession()
    }


    fun logout(context: Context) {
        val userPreference = provideSession(context)
        return userPreference.logout()
    }

}