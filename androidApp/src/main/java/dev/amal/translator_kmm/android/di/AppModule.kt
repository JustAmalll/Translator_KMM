package dev.amal.translator_kmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amal.translator_kmm.database.TranslateDatabase
import dev.amal.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import dev.amal.translator_kmm.translate.data.local.DatabaseDriverFactory
import dev.amal.translator_kmm.translate.data.remote.HttpClientFactory
import dev.amal.translator_kmm.translate.data.translate.KtorTranslateClient
import dev.amal.translator_kmm.translate.domain.history.HistoryDataSource
import dev.amal.translator_kmm.translate.domain.translate.Translate
import dev.amal.translator_kmm.translate.domain.translate.TranslateClient
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideTranslateClient(
        httpClient: HttpClient
    ): TranslateClient = KtorTranslateClient(httpClient)

    @Provides
    @Singleton
    fun provideDatabaseDriver(
        app: Application
    ): SqlDriver = DatabaseDriverFactory(app).create()

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource =
        SqlDelightHistoryDataSource(TranslateDatabase(driver))

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient, dataSource: HistoryDataSource
    ): Translate = Translate(client, dataSource)
}