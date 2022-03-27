package com.proyekakhir.core.di

import androidx.room.Room
import com.proyekakhir.core.BuildConfig
import com.proyekakhir.core.data.source.LocalDataSource
import com.proyekakhir.core.data.source.RemoteDataSource
import com.proyekakhir.core.data.source.local.DatabaseSuite
import com.proyekakhir.core.data.source.remote.network.ApiService
import com.proyekakhir.core.domain.repository.EventRepository
import com.proyekakhir.core.domain.usecase.EventIteractor
import com.proyekakhir.core.domain.usecase.UseCaseEvent
import com.proyekakhir.core.utils.SSLCertificateConfigurator
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager


val coreModule = module {
    single { EventRepository(get(),get()) }
    factory<UseCaseEvent> { EventIteractor(get()) }
    single {
        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(androidContext())
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .sslSocketFactory(SSLCertificateConfigurator.getSSLConfiguration(androidContext()).socketFactory, trustManager)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build().create(ApiService::class.java)
    }
    factory { RemoteDataSource(get()) }
    factory {
        get<DatabaseSuite>().suiteDao
    }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("Razit".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), DatabaseSuite::class.java, "db_SUITES1")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    factory { LocalDataSource(get()) }
}