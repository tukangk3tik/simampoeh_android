package app.trikode.simampoeh.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log.d
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.trikode.simampoeh.R
import app.trikode.simampoeh.ui.main_non_login.MainNonLoginActivity
import app.trikode.simampoeh.utils.session.SessionHelper
import kotlin.system.exitProcess

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var permissionCount = 0

        if (!checkCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                1)
        } else {
            permissionCount += 1
        }

        if (permissionCount > 0) routeTo()
    }

    private fun checkCameraPermission(): Boolean {
        val permission = Manifest.permission.CAMERA
        val res = applicationContext.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    private fun routeTo() {
        lateinit var route: Class<*>

        if (SessionHelper.checkLogin(this)){
            route = MainActivity::class.java
        } else {
            route = MainNonLoginActivity::class.java
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, route)
            startActivity(intent)
            finish()
        }, 1000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> if (
                grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        val msg = "Allow Camera?"

                        showMessageOKCancel(msg) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermission()
                        }
                    } else {
                        routeTo()
                    }
                }
            } else {
                d("PERMISSION", "Permission denied")
            }

            0 -> {
                finish()
                exitProcess(0)
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            1
        )
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: (Any, Any) -> Unit
    ) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}