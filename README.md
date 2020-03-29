# Profile Demo App

![Android CI with Gradle](https://github.com/tomkoptel/profile-app/workflows/Android%20CI%20with%20Gradle/badge.svg)

## [Get the App](https://github.com/tomkoptel/profile-app/suites/556413120/artifacts/3560017)
You can find the latest version inside [Actions Tab](https://github.com/tomkoptel/profile-app/actions).

## Tech Used
* [Android Jetpack](https://developer.android.com/jetpack/androidx)
* [Motion Layout](https://developer.android.com/training/constraint-layout/motionlayout/examples)
* [Ktor](https://ktor.io/quickstart/index.html) + [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) + [OkHttp](https://square.github.io/okhttp/)
* [Kotlin Test + Kotlin JUnit 4 Integration](https://kotlinlang.org/api/latest/kotlin.test/)
* [Use Gradle Profile Plugin for BOM Generation](https://guides.gradle.org/performance/)
* [Github Actions as hosting CI/CD system](https://github.com/features/actions).

## Architecture
So far it is a single module setup with `platform` Gradle submodule to align version configurations.

The basic idea is to keep 3 levels of responsibility:
* data - holds implementation of data sources and respective mappers for domain layer.
* domain - or interface, or contract layer defines pure interfaces and data classes.
* presentation - consumes domain layer directly has no knowledge about data layer.

# TODO
- [] Add support for DI Service Locator(aka Koin).
- [] Remove Glide annotation compiler.
- [] Smooth out animation for loading profile image + name inside MotionLayout.
- [] Cover Fragment with headless Robolectric 4 + Espresson setup.
- [] Configure Ktlint + Checkstyle.
- [] Configure SonarQube.
- [] Extend endpoint to provide list of companies.
- [] Implement widget with a list of companies.
- [] Extend endpoint to provide list of technologies.
- [] Implement widget with a list of technologies.
- [] Consume LinkedIN API instead of custom endpoint implementation.

# App
![App](https://github.com/tomkoptel/profile-app/blob/master/app.gif?raw=true)
