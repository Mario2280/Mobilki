package com.david.laba.application

import android.app.Application
import androidx.room.Room
import com.david.laba.auxiliary.AppSchedulers
import com.david.laba.db.AppDatabase
import com.david.laba.net.service.UserService
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private lateinit var kodeinStored: DI

class App : Application() {
    companion object {
        val di: DI
            get() = kodeinStored
    }

    private val baseModule = DI.Module("baseModule") {
        import(dbModule)
        import(auxiliaryModule)
        import(netModule)
    }

    private val dbModule = DI.Module("dbModule") {
        constant("dbName") with "tranty-database"

        bind<AppDatabase>() with singleton {
            Room
                .databaseBuilder(baseContext, AppDatabase::class.java, instance("dbName"))
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    private val netModule = DI.Module("netModule") {
        constant("apiUrl") with "http://192.168.132.99:8000"

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(instance<String>("apiUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        bind<UserService>() with singleton {
            instance<Retrofit>().create(UserService::class.java)
        }
    }

    private val auxiliaryModule = DI.Module("auxiliaryModule") {
        bind<AppSchedulers>() with singleton {
            AppSchedulers()
        }
    }

    override fun onCreate() {
        if (::kodeinStored.isInitialized.not()) {
            kodeinStored = DI {
                import(baseModule)
            }
        }
        super.onCreate()
    }
}