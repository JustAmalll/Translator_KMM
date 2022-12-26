package dev.amal.translator_kmm.di

import dev.amal.translator_kmm.database.TranslateDatabase
import dev.amal.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import dev.amal.translator_kmm.translate.data.local.DatabaseDriverFactory
import dev.amal.translator_kmm.translate.data.remote.HttpClientFactory
import dev.amal.translator_kmm.translate.data.translate.KtorTranslateClient
import dev.amal.translator_kmm.translate.domain.history.HistoryDataSource
import dev.amal.translator_kmm.translate.domain.translate.Translate
import dev.amal.translator_kmm.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }
}