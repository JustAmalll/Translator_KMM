package dev.amal.translator_kmm.di

import dev.amal.translator_kmm.database.TranslateDatabase
import dev.amal.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import dev.amal.translator_kmm.translate.data.local.DatabaseDriverFactory
import dev.amal.translator_kmm.translate.data.remote.HttpClientFactory
import dev.amal.translator_kmm.translate.data.translate.KtorTranslateClient
import dev.amal.translator_kmm.translate.domain.history.HistoryDataSource
import dev.amal.translator_kmm.translate.domain.translate.Translate
import dev.amal.translator_kmm.translate.domain.translate.TranslateClient
import dev.amal.translator_kmm.voice_to_text.domain.VoiceToTextParser

interface AppModule {
    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: Translate
    val voiceToTextParser: VoiceToTextParser
}

class AppModuleImpl(parser: VoiceToTextParser) : AppModule {

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    override val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    override val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }

    override val voiceToTextParser = parser
}