package app.trikode.simampoeh.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.trikode.simampoeh.core.di.Injection
import app.trikode.simampoeh.domain.repository.IAppRepository
import app.trikode.simampoeh.ui.general_form.upload_berkas.UploadBerkasViewModel
import app.trikode.simampoeh.ui.login.LoginViewModel
import app.trikode.simampoeh.ui.lupa_password.LupaPasswordViewModel
import app.trikode.simampoeh.ui.pengajuan.PengajuanViewModel
import app.trikode.simampoeh.ui.register.RegisterViewModel
import app.trikode.simampoeh.ui.tagihan.TagihanViewModel
import app.trikode.simampoeh.ui.utils.KirimLayananViewModel
import app.trikode.simampoeh.ui.utils.SearchViewModel
import app.trikode.simampoeh.ui.utils.option.OptionMenuViewModel

class ViewModelFactory private constructor(private val appRepository: IAppRepository)
    : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when  {
            modelClass.isAssignableFrom(OptionMenuViewModel::class.java) -> {
                OptionMenuViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(UploadBerkasViewModel::class.java) -> {
                UploadBerkasViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(KirimLayananViewModel::class.java) -> {
                KirimLayananViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(LupaPasswordViewModel::class.java) -> {
                LupaPasswordViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(PengajuanViewModel::class.java) -> {
                PengajuanViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(TagihanViewModel::class.java) -> {
                TagihanViewModel(appRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}