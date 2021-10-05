package com.example.flendzz.di


import com.example.flendzz.info_show.ui.allmatches.EmpListViewModel

import com.example.flendzz.info_show.ui.save.EmpDetailsViewModel
import com.example.flendzz.network.ApiInterface
import com.example.flendzz.network.LocationRepository
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module {
    viewModel {
        EmpListViewModel(get(),get() )
    }
}

val repositoryModule = module {
    single {
        LocationRepository(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun     provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(factory))
                .client(client)
                .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}




val viewModelModuleLocal = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
       EmpDetailsViewModel(get(), get())
    }

}

