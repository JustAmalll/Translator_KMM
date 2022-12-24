package dev.amal.translator_kmm.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(error: TranslateError): Exception(
    "An error occurred when translating: $error"
)