package dev.amal.translator_kmm.presentation

import android.Manifest
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.amal.translator_kmm.android.MainActivity
import kotlinx.coroutines.runBlocking
import dev.amal.translator_kmm.android.R
import dev.amal.translator_kmm.android.di.AppModule
import dev.amal.translator_kmm.android.voice_to_text.di.VoiceToTextModule
import dev.amal.translator_kmm.translate.data.remote.FakeTranslateClient
import dev.amal.translator_kmm.translate.domain.translate.TranslateClient
import dev.amal.translator_kmm.voice_to_text.data.FakeVoiceToTextParser
import dev.amal.translator_kmm.voice_to_text.domain.VoiceToTextParser
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceToTextParser: VoiceToTextParser

    @Inject
    lateinit var fakeTranslateClient: TranslateClient

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val parser = fakeVoiceToTextParser as FakeVoiceToTextParser
        val client = fakeTranslateClient as FakeTranslateClient

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onNodeWithText(client.translatedText)
            .assertIsDisplayed()
    }
}