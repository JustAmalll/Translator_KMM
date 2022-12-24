package dev.amal.translator_kmm.translate.data.history

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.amal.translator_kmm.core.domain.util.CommonFlow
import dev.amal.translator_kmm.core.domain.util.toCommonFlow
import dev.amal.translator_kmm.database.TranslateDatabase
import dev.amal.translator_kmm.translate.domain.history.HistoryDataSource
import dev.amal.translator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
): HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history -> history.map { it.toHistoryItem() } }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}