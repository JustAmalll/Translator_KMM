package dev.amal.translator_kmm.android.voice_to_text.presentation

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.amal.translator_kmm.android.R
import dev.amal.translator_kmm.android.core.theme.LightBlue
import dev.amal.translator_kmm.android.voice_to_text.presentation.components.VoiceRecorderDisplay
import dev.amal.translator_kmm.voice_to_text.presentation.DisplayState
import dev.amal.translator_kmm.voice_to_text.presentation.VoiceToTextEvent
import dev.amal.translator_kmm.voice_to_text.presentation.VoiceToTextState

@ExperimentalAnimationApi
@Composable
fun VoiceToTextScreen(
    state: VoiceToTextState,
    languageCode: String,
    onResult: (String) -> Unit,
    onEvent: (VoiceToTextEvent) -> Unit
) {
    val context = LocalContext.current
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onEvent(
                VoiceToTextEvent.PermissionResult(
                    isGranted = isGranted,
                    isPermanentlyDeclined = !isGranted && !(context as ComponentActivity)
                        .shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)
                )
            )
        }
    )

    LaunchedEffect(recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FloatingActionButton(
                    modifier = Modifier.size(75.dp),
                    onClick = {
                        if (state.displayState != DisplayState.DISPLAYING_RESULTS) {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        } else {
                            onResult(state.spokenText)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                ) {
                    AnimatedContent(targetState = state.displayState) { displayState ->
                        when (displayState) {
                            DisplayState.SPEAKING -> Icon(
                                modifier = Modifier.size(50.dp),
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(id = R.string.stop_recording)
                            )
                            DisplayState.DISPLAYING_RESULTS -> Icon(
                                modifier = Modifier.size(50.dp),
                                imageVector = Icons.Rounded.Check,
                                contentDescription = stringResource(id = R.string.apply)
                            )
                            else -> Icon(
                                modifier = Modifier.size(50.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                                contentDescription = stringResource(id = R.string.record_audio)
                            )
                        }
                    }
                }
                if (state.displayState == DisplayState.DISPLAYING_RESULTS) {
                    IconButton(
                        onClick = {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = stringResource(id = R.string.record_again),
                            tint = LightBlue
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { onEvent(VoiceToTextEvent.Close) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
                if (state.displayState == DisplayState.SPEAKING) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.listening),
                        color = LightBlue
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(targetState = state.displayState) { displayState ->
                    when (displayState) {
                        DisplayState.WAITING_TO_TALK -> Text(
                            text = stringResource(id = R.string.start_talking),
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center
                        )
                        DisplayState.SPEAKING -> {
                            VoiceRecorderDisplay(powerRatios = state.powerRatios)
                        }
                        DisplayState.DISPLAYING_RESULTS -> Text(
                            text = state.spokenText,
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center
                        )
                        DisplayState.ERROR -> Text(
                            text = state.recordError ?: "Unknown error",
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.error
                        )
                        null -> Unit
                    }
                }
            }
        }
    }
}