package app.trikode.simampoeh.core.di

import android.app.Application
import android.content.Context
import app.trikode.simampoeh.core.data.source.AppRepository
import app.trikode.simampoeh.core.data.source.remote.RemoteDataSource
import app.trikode.simampoeh.core.data.source.remote.network.ApiConfig
import app.trikode.simampoeh.core.utils.AppExecutors
import app.trikode.simampoeh.domain.repository.IAppRepository

object Injection {

    fun provideRemoteDataSource(application: Application): RemoteDataSource {
        return RemoteDataSource.getInstance(ApiConfig.provideApiService(application.applicationContext))
    }

    fun provideRepository(context: Context): IAppRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService(context))
        val appExecutors = AppExecutors()

        return AppRepository.getInstance(remoteDataSource, appExecutors)
    }

}