package dev.amal.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.amal.translator_kmm.android.R
import dev.amal.translator_kmm.android.core.theme.LightBlue
import dev.amal.translator_kmm.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    modifier: Modifier = Modifier,
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            UiLanguage.allLanguages.forEach { language ->
                LanguageDropDownItem(
                    modifier = Modifier.fillMaxWidth(),
                    language = language,
                    onClick = { onSelectLanguage(language) }
                )
            }
        }
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(30.dp),
                model = language.drawableRes,
                contentDescription = language.language.langName
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = language.language.langName, color = LightBlue)
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = if (isOpen) Icons.Default.ArrowDropUp
                else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen) stringResource(id = R.string.close)
                else stringResource(id = R.string.open),
                tint = LightBlue
            )
        }
    }
}