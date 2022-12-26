package dev.amal.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.amal.translator_kmm.android.core.theme.LightBlue
import dev.amal.translator_kmm.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    modifier: Modifier = Modifier,
    language: UiLanguage
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(language = language)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = language.language.langName, color = LightBlue)
    }
}