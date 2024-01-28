package solis.jhon.pokezeus.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import solis.jhon.pokezeus.data.database.DataBase
import solis.jhon.pokezeus.data.database.PokemonDao
import solis.jhon.pokezeus.data.network.PokemonInterceptor
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.repository.PokemonRepositoryImpl
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpClient(apiKeyInterceptor: PokemonInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providePokemonService(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): PokemonService =
        Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
            .create(PokemonService::class.java)

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            DataBase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun providePokemonDao(dataBase: DataBase): PokemonDao =
        dataBase.pokemonDao()

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
    }
}