package solis.jhon.pokezeus.data.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import solis.jhon.pokezeus.data.database.PokemonDao
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.repository.PokemonRepositoryImpl
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.presentation.factory.PokemonViewModelFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideApiService(): PokemonService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    @Provides
    fun provideRepository(apiService: PokemonService, dao: PokemonDao): PokemonRepository {
        return PokemonRepositoryImpl(apiService, dao)
    }

    @Provides
    fun provideUseCase(repository: PokemonRepository, context: Context): PokemonUseCase {
        return PokemonUseCase(repository, context)
    }

    @Provides
    fun provideViewModelFactory(useCase: PokemonUseCase): ViewModelProvider.Factory {
        return PokemonViewModelFactory(useCase)
    }

}