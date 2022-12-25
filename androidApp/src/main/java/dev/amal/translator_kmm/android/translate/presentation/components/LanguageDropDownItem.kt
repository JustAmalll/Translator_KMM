package dev.amal.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.amal.translator_kmm.core.presentation.UiLanguage

@Composable
fun LanguageDropDownItem(
    modifier: Modifier = Modifier,
    language: UiLanguage,
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick = onClick, modifier = modifier) {
        Image(
            painter = painterResource(id = language.drawableRes),
            contentDescription = language.language.langName,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = language.language.langName)
    }
}