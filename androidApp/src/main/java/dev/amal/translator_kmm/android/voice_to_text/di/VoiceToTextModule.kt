package dev.amal.translator_kmm.android.voice_to_text.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.amal.translator_kmm.android.voice_to_text.data.AndroidVoiceToTextParser
import dev.amal.translator_kmm.voice_to_text.domain.VoiceToTextParser

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(app: Application): VoiceToTextParser =
        AndroidVoiceToTextParser(app)
}