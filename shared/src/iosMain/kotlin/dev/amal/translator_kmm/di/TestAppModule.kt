package dev.amal.translator_kmm.di

import dev.amal.translator_kmm.testing.FakeHistoryDataSource
import dev.amal.translator_kmm.testing.FakeTranslateClient
import dev.amal.translator_kmm.testing.FakeVoiceToTextParser
import dev.amal.translator_kmm.translate.domain.translate.Translate
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class TestAppModule: AppModule {
    override val historyDataSource = FakeHistoryDataSource()
    override val translateClient = FakeTranslateClient()
    override val translateUseCase = Translate(translateClient, historyDataSource)
    override val voiceToTextParser = FakeVoiceToTextParser()
}