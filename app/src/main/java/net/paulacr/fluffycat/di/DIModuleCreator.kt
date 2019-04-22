package net.paulacr.fluffycat.di

import net.paulacr.fluffycat.ui.catdashboard.CatDashboardRepository
import net.paulacr.fluffycat.ui.catdashboard.CatDashboardViewModel
import net.paulacr.fluffycat.network.NetworkInstance.generalApi
import net.paulacr.fluffycat.ui.fluffycat.FluffyCatActivityViewModel
import net.paulacr.fluffycat.ui.home.HomeActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val petModule = module {
    viewModel { HomeActivityViewModel(androidApplication()) }
    viewModel { CatDashboardViewModel(androidApplication(), get()) }
    viewModel { FluffyCatActivityViewModel(androidApplication(), get()) }
}

val petRepositoryModule = module {
    single { CatDashboardRepository(get()) }
}

val networkModule = module {
    single { generalApi }
}

val petApp = listOf(petModule, networkModule, petRepositoryModule)
