package dev.amal.translator_kmm.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import dev.amal.translator_kmm.core.presentation.UiLanguage
import dev.amal.translator_kmm.translate.data.local.FakeHistoryDataSource
import dev.amal.translator_kmm.translate.data.remote.FakeTranslateClient
import dev.amal.translator_kmm.translate.domain.history.HistoryItem
import dev.amal.translator_kmm.translate.domain.translate.Translate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setup() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = Translate(
            client = client,
            historyDataSource = dataSource
        )
        viewModel = TranslateViewModel(
            translate = translate,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "ru",
                fromText = "Привет",
                toLanguageCode = "en",
                toText = "Hello"
            )
            dataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expectedItem = UiHistoryItem(
                id = item.id!!,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                fromText = item.fromText,
                toLanguage = UiLanguage.byCode(item.toLanguageCode),
                toText = item.toText
            )
            assertThat(state.history.first()).isEqualTo(expectedItem)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}