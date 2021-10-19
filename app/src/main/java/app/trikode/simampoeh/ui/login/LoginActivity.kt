package app.trikode.simampoeh.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.data.source.remote.utils.ResponseResultHandler
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityLoginBinding
import app.trikode.simampoeh.databinding.ProgressLayoutDarkBinding
import app.trikode.simampoeh.domain.model.user.User
import app.trikode.simampoeh.ui.MainActivity
import app.trikode.simampoeh.ui.lupa_password.LupaPasswordActivity
import app.trikode.simampoeh.ui.main_non_login.MainNonLoginActivity
import app.trikode.simampoeh.ui.register.RegisterActivity
import app.trikode.simampoeh.utils.general.AlertDialogHelper
import app.trikode.simampoeh.utils.general.EventLiveData
import app.trikode.simampoeh.utils.general.GeneralUtils
import app.trikode.simampoeh.utils.session.SessionHelper
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBinding: ProgressLayoutDarkBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        progressBinding = binding.progressLayout
        setContentView(binding.root)

        loginViewModel = obtainViewModel(this)

        binding.btnLogin.setOnClickListener(this)
        binding.btnDaftar.setOnClickListener(this)
        binding.lupaPassword.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

        loginViewModel.userData.observe(this, userDataObserver)
        loginViewModel.dialogLoginHandler.observe(this, dialogObserver)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_login -> {
                val nik = binding.edtNik.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.checkStringLength(nik, 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(password) -> binding.edtPassword.error = GeneralUtils.FIELD_REQUIRED
                    else -> {
                        loginViewModel.login(nik, password)
                    }
                }
            }
            R.id.btn_daftar -> {
                val mIntent = Intent(this, RegisterActivity::class.java)
                startActivity(mIntent)
            }
            R.id.lupa_password -> {
                val mIntent = Intent(this, LupaPasswordActivity::class.java)
                startActivity(mIntent)
            }
            R.id.btnBack -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        val mIntent = Intent(this, MainNonLoginActivity::class.java)
        finish()
        startActivity(mIntent)
    }

    private val userDataObserver = Observer<ApiResponse<User>> { response ->
        when(response) {
            is ApiResponse.Success -> {
                response.data?.let {
                    SessionHelper.saveSession(it, this)
                }
            }
            is ApiResponse.Error -> {
                Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
            is ApiResponse.Loading -> {
                showLoading(true)
            }
        }
    }

    private val dialogObserver = Observer<EventLiveData<ResponseResultHandler>> {
        it.getContentIfNotHandled()?.let { handler ->
            if (handler.responseResult > 0) {
                val mIntent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(mIntent)
            } else {
                AlertDialogHelper.showAlertDialog(handler.responseMessage, this)
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBinding.root.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            binding.btnDaftar.isEnabled = false
            binding.btnBack.isEnabled = false
            binding.lupaPassword.isClickable = false
        } else {
            progressBinding.root.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            binding.btnDaftar.isEnabled = true
            binding.btnBack.isEnabled = true
            binding.lupaPassword.isClickable = true
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }


}