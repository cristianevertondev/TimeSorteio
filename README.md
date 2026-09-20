# TimeSorteio

A sports team draw and match organization application for Android, developed with **Kotlin** and **Jetpack Compose**.

TimeSorteio helps users organize recreational matches by configuring the sport, number of players, teams, and player names, then automatically generating balanced team assignments and presenting the final result in an easy-to-follow format.

## Features

* Sport selection
* Custom match configuration
* Total player configuration
* Players-per-team configuration
* Team count configuration
* Player name registration
* Team selection and organization
* Match configuration review
* Automatic team draw generation
* Result formatting
* PDF result generation
* Sound effects
* Input validation and error handling
* Responsive Jetpack Compose interface
* Support for Portuguese, English, and Spanish
* Unit tests
* Android instrumentation tests

## Technologies

* **Kotlin**
* **Jetpack Compose**
* **Android SDK**
* **Android Jetpack**
* **ViewModel**
* **Navigation Compose**
* **Gradle Kotlin DSL**
* **JUnit**
* **Android Instrumentation Tests**
* **Android Audio APIs**

## Architecture

The project follows a structured architecture designed to separate business logic, presentation, navigation, and reusable UI components.

### Domain

The `domain` layer contains the core application models and use cases.

```text
domain/
├── model/
│   ├── DrawResult.kt
│   ├── MatchConfig.kt
│   ├── Player.kt
│   ├── Sport.kt
│   └── Team.kt
│
└── usecase/
    ├── FormatResultUseCase.kt
    ├── GenerateDrawUseCase.kt
    ├── GeneratePdfUseCase.kt
    ├── ValidateMatchConfigUseCase.kt
    └── ValidationResult.kt
```

### Presentation

The presentation layer contains the application's screens, reusable components, navigation, and UI state management.

```text
presentation/
├── components/
├── navigation/
├── screens/
└── viewmodel/
```

The application uses a `ViewModel` to manage match configuration and UI state throughout the different stages of the team-drawing process.

## Application Flow

The application guides the user through a structured configuration flow:

```text
Sport Selection
      ↓
Total Players
      ↓
Players per Team
      ↓
Team Configuration
      ↓
Player Names
      ↓
Review
      ↓
Team Draw
      ↓
Formatted Result
      ↓
PDF Generation
```

This approach keeps the configuration process organized while allowing the user to review the match settings before generating the final teams.

## Team Draw System

The core functionality is implemented through dedicated domain use cases, including:

* Match configuration validation
* Team draw generation
* Result formatting
* PDF generation

This separation keeps the business rules independent from the UI layer and makes the core logic easier to test and maintain.

## Audio

TimeSorteio includes a dedicated `SoundManager` utility for handling application sound effects.

```text
util/
└── SoundManager.kt
```

## Localization

The application supports three languages:

* 🇧🇷 Portuguese
* 🇺🇸 English
* 🇪🇸 Spanish

Localized resources are maintained using Android's standard resource system.

## Testing

The project includes both:

* Unit tests
* Android instrumentation tests

Testing focuses on validating application logic and ensuring reliable behavior across the Android environment.

## Project Structure

```text
app/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── ...
    │   └── res/
    │       ├── values/
    │       ├── values-en/
    │       └── values-es/
    │
    └── test/
```

## Getting Started

### Requirements

* Android Studio
* JDK
* Android SDK
* Android device or emulator

### Build

Clone the repository:

```bash
git clone https://github.com/cristianevertondev/TimeSorteio.git
```

Open the project in Android Studio, allow Gradle to synchronize, and run the application on an Android device or emulator.

## Project Goals

TimeSorteio was created as part of the **ELDREON STUDIOS** Android development portfolio, with a focus on:

* Modern Android development
* Kotlin-first development
* Jetpack Compose
* Clean separation of responsibilities
* Reusable UI components
* Testable business logic
* Multilingual applications
* Practical mobile application development

## Developer

**ELDREON STUDIOS**

Android development focused on **Kotlin, Jetpack Compose, mobile applications, and games**.

GitHub:
https://github.com/cristianevertondev

LinkedIn:
www.linkedin.com/in/cristian-everton-30388b438

---

## License

This project is part of the ELDREON STUDIOS development portfolio.
