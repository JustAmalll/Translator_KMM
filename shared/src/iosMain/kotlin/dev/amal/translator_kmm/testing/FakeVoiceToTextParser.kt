package dev.amal.translator_kmm.testing

import dev.amal.translator_kmm.core.domain.util.CommonStateFlow
import dev.amal.translator_kmm.core.domain.util.toCommonStateFlow
import dev.amal.translator_kmm.voice_to_text.domain.VoiceToTextParser
import dev.amal.translator_kmm.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser: VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    var voiceResult = "test result"

    override fun startListening(languageCode: String) {
        _state.update {
            it.copy(result = "", isSpeaking = true)
        }
    }

    override fun stopListening() {
        _state.update {
            it.copy(result = voiceResult, isSpeaking = false)
        }
    }

    override fun cancel() = Unit

    override fun reset() {
        _state.update { VoiceToTextParserState() }
    }
}