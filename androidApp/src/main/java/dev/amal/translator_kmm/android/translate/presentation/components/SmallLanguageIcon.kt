package dev.amal.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.amal.translator_kmm.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    modifier: Modifier = Modifier,
    language: UiLanguage
) {
    AsyncImage(
        modifier = modifier.size(25.dp),
        model = language.drawableRes,
        contentDescription = language.language.langName
    )
}