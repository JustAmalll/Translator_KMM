package dev.amal.translator_kmm.translate.presentation

import dev.amal.translator_kmm.core.presentation.UiLanguage
import dev.amal.translator_kmm.translate.domain.translate.TranslateError

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("ru"),
    val toLanguage: UiLanguage = UiLanguage.byCode("en"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)
