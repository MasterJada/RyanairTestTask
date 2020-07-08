package dev.jetlaunch.ryanairtesttask.di

import com.google.gson.Gson
import dev.jetlaunch.ryanairtesttask.model.StationsRepository
import dev.jetlaunch.ryanairtesttask.model.TripRepository
import dev.jetlaunch.ryanairtesttask.networking.RyanairApiService
import dev.jetlaunch.ryanairtesttask.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single<RyanairApiService> {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.ryanair.com/api/booking/v4/en-gb/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        retrofit.create(RyanairApiService::class.java)
    }
    single { StationsRepository(get()) }
    single { TripRepository(get()) }
    viewModel { SearchViewModel(get(), get()) }
}