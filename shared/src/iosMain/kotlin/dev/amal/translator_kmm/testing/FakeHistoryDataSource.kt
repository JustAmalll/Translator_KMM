package dev.amal.translator_kmm.testing

import dev.amal.translator_kmm.core.domain.util.CommonFlow
import dev.amal.translator_kmm.core.domain.util.toCommonFlow
import dev.amal.translator_kmm.translate.domain.history.HistoryDataSource
import dev.amal.translator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@DelicateCoroutinesApi
class FakeHistoryDataSource: HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }
}