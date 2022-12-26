package dev.amal.translator_kmm.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.amal.translator_kmm.android.R
import dev.amal.translator_kmm.android.core.theme.LightBlue
import dev.amal.translator_kmm.core.presentation.UiLanguage

@ExperimentalAnimationApi
@Composable
fun TranslateTextField(
    modifier: Modifier = Modifier,
    fromText: String,
    toText: String?,
    isTranslating: Boolean,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onTextFieldClick)
            .padding(16.dp)
    ) {
        AnimatedContent(targetState = toText) { toText ->
            if (toText == null || isTranslating) IdleTranslateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                fromText = fromText,
                isTranslating = isTranslating,
                onTextChange = onTextChange,
                onTranslateClick = onTranslateClick
            )
            else TranslatedTextField(
                modifier = Modifier.fillMaxWidth(),
                fromText = fromText,
                toText = toText,
                fromLanguage = fromLanguage,
                toLanguage = toLanguage,
                onCopyClick = onCopyClick,
                onCloseClick = onCloseClick,
                onSpeakerClick = onSpeakerClick
            )
        }
    }
}

@Composable
private fun TranslatedTextField(
    modifier: Modifier = Modifier,
    fromText: String,
    toText: String,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit
) {
    Column(modifier = modifier) {
        LanguageDisplay(language = fromLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = fromText, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { onCopyClick(fromText) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.copy),
                    contentDescription = stringResource(id = R.string.copy),
                    tint = LightBlue
                )
            }
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = LightBlue
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        LanguageDisplay(language = toLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = toText, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { onCopyClick(toText) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.copy),
                    contentDescription = stringResource(id = R.string.copy),
                    tint = LightBlue
                )
            }
            IconButton(onClick = onSpeakerClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.speaker),
                    contentDescription = stringResource(id = R.string.play_loud),
                    tint = LightBlue
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun IdleTranslateTextField(
    fromText: String,
    isTranslating: Boolean,
    onTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { isFocused = it.isFocused },
            value = fromText,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
        )
        if (fromText.isEmpty() && !isFocused) {
            Text(
                text = stringResource(id = R.string.enter_a_text_to_translate),
                color = LightBlue
            )
        }
        ProgressButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.translate),
            isLoading = isTranslating,
            onClick = onTranslateClick
        )
    }
}