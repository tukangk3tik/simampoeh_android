package app.trikode.simampoeh.ui.register

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityRegisterBinding
import app.trikode.simampoeh.databinding.ProgressLayoutDarkBinding
import app.trikode.simampoeh.utils.general.GeneralUtils

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressLayoutDarkBinding: ProgressLayoutDarkBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        progressLayoutDarkBinding = binding.progressBar
        setContentView(binding.root)

        registerViewModel = obtainViewModel(this)

        binding.btnBack.setOnClickListener(this)
        binding.btnDaftar.setOnClickListener(this)

        registerViewModel.dialogLoginHandler.observe(this, {
            it.getContentIfNotHandled()?.let { result ->
                showDialogResult(this, result.responseMessage, result.responseResult)
                isLoading(false)
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnBack.id -> onBackPressed()
            binding.btnDaftar.id -> {
                val nik = binding.edtNik.text.toString().trim()
                val nokk = binding.edtNoKk.text.toString().trim()
                val email = binding.edtEmail.text.toString().trim()
                val nohp = binding.edtNoHp.text.toString().trim()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.checkStringLength(nik, 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(nokk) -> binding.edtNoKk.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.checkStringLength(nokk, 16) -> binding.edtNoKk.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(email) -> binding.edtEmail.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.isValidEmail(email) -> binding.edtEmail.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(nohp) -> binding.edtNoHp.error = GeneralUtils.FIELD_REQUIRED
                    else -> {
                        isLoading(true)
                        registerViewModel.register(nik, nokk, email, nohp)
                    }
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RegisterViewModel::class.java)
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            progressLayoutDarkBinding.root.visibility = View.VISIBLE
            binding.btnDaftar.isEnabled = false
        } else {
            progressLayoutDarkBinding.root.visibility = View.GONE
        }
    }

    private fun showDialogResult(
        context: Context,
        message: String,
        responseResult: Int
    ) {
        AlertDialog.Builder(context)
            .setTitle("Informasi")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                if (responseResult > 0){
                    finish()
                }
            }
            .create()
            .show()
    }

}