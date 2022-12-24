package dev.amal.translator_kmm.translate.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import dev.amal.translator_kmm.database.TranslateDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(
        TranslateDatabase.Schema, "translate.db"
    )
}