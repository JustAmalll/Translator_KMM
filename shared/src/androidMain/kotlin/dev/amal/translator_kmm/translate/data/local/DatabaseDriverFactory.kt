package dev.amal.translator_kmm.translate.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dev.amal.translator_kmm.database.TranslateDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(TranslateDatabase.Schema, context, "translate.db")
}