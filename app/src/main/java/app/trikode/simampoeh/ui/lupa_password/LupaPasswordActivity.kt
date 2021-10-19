package app.trikode.simampoeh.ui.lupa_password

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.ActivityLupaPasswordBinding
import app.trikode.simampoeh.databinding.ProgressLayoutDarkBinding
import app.trikode.simampoeh.utils.general.GeneralUtils

class LupaPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLupaPasswordBinding
    private lateinit var progressLayoutDarkBinding: ProgressLayoutDarkBinding
    private lateinit var lupaPasswordViewModel: LupaPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        lupaPasswordViewModel = ViewModelProvider(this, factory)[LupaPasswordViewModel::class.java]

        binding.btnBack.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)

        lupaPasswordViewModel.dialogLoginHandler.observe(this, {
            it.getContentIfNotHandled()?.let { result ->
                showDialogResult(this, result.responseMessage, result.responseResult)
                isLoading(false)
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> onBackPressed()
            binding.btnReset.id -> {
                val nik = binding.edtNik.text.toString().trim()
                val email = binding.edtEmail.text.toString().trim()

                when {
                    TextUtils.isEmpty(nik) -> binding.edtNik.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.checkStringLength(nik, 16) -> binding.edtNik.error = GeneralUtils.WRONG_FORMAT
                    TextUtils.isEmpty(email) -> binding.edtEmail.error = GeneralUtils.FIELD_REQUIRED
                    !GeneralUtils.isValidEmail(email) -> binding.edtEmail.error = GeneralUtils.WRONG_FORMAT
                    else -> {
                        lupaPasswordViewModel.resetPassword(nik, email)
                        isLoading(true)
                    }
                }
            }
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            progressLayoutDarkBinding.root.visibility = View.VISIBLE
            binding.btnReset.isEnabled = false
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