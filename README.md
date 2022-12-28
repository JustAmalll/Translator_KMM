# Kotlin Multiplatform Mobile Translator App

## What is KMM?

KMM stands for Kotlin Multiplatform Mobile and is a brand new technology that allows us to share large portions of Kotlin code between Android and iOS.
The Kotlin code will then be translated into native iOS code, so that you will get 2 native apps in the end, which is the biggest advantage of this technology. Only the platform specific logic needs to be implemented twice in native language. That includes the UI and platform APIs such as showing notifications or speech recognition.

## Features

### Translator app

* Interacting with remote APIs in KMM projects using Ktor
* Local databases in KMM using SQLDelight
* Reactive programming with Kotlin Flows and observing these on all platforms
* Sharing ViewModels and state mapping logic between Android & iOS
* Professionally designed translator screen using Jetpack Compose and SwiftUI
* Proper dependency injection in KMM projects
* Theming on Android & iOS

### Speech Recognition

* Platform specific APIs such as the speech recognition API in KMM projects
* Testable speech recognizer that fits well into an existing architecture
* Beautiful animated voice recorder UI component as you know from WhatsApp

### Testing

* Local unit tests for shared Kotlin code
* UI and end-to-end tests for both Android & iOS to test real user flows such as voice input and translation

![translator_kmm_preview](https://user-images.githubusercontent.com/85354530/209861420-ae434a47-3bc2-4e46-9be4-2c9ed0289e56.png)
