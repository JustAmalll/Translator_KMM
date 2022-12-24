package dev.amal.translator_kmm.translate.data.history

import database.HistoryEntity
import dev.amal.translator_kmm.translate.domain.history.HistoryItem

fun HistoryEntity.toHistoryItem(): HistoryItem = HistoryItem(
    id = id,
    fromLanguageCode = fromLanguageCode,
    fromText = fromText,
    toLanguageCode = toLanguageCode,
    toText = toText
)