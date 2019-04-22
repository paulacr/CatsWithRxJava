package net.paulacr.fluffycat

import android.app.Application
import net.paulacr.fluffycat.di.petApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(petApp).androidContext(applicationContext)
        }
    }
}