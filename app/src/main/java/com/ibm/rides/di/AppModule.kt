package com.ibm.rides.di

import com.ibm.rides.core.utils.ClientApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }


    @Provides
    @Singleton
    fun providesClientApiManager(client: HttpClient): ClientApiManager {
        return ClientApiManager(client)
    }
}