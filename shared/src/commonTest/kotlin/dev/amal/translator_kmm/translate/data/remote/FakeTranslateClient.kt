package dev.amal.translator_kmm.translate.data.remote

import dev.amal.translator_kmm.core.domain.language.Language
import dev.amal.translator_kmm.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}