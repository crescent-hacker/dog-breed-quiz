# Android Project: Dog Breed Quiz

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)
![Language](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Status](https://img.shields.io/badge/Status-Under%20Development-orange.svg)

## 📖 Overview

Dog Breed Quiz is an Android application designed to help dog lovers to learn breeds.

## ✨ Features

- Feature 1: Home, show greetings.
<p>
  <img src="screenshots/screenshot_1.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_5.png" alt="Home Screen" width="20%" />
</p>

- Feature 2: Quiz, show random a dog breed image and help user to identify what breed it is.
<p>
  <img src="screenshots/screenshot_2.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_3.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_4.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_6.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_7.png" alt="Home Screen" width="20%" />
  <img src="screenshots/screenshot_8.png" alt="Home Screen" width="20%" />
</p>

## 🔧 Tech Stack

The app is built using the following technologies:

- **Language**: Kotlin
- **Architecture**: MVI
- **UI Toolkit**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Networking**: Retrofit/OkHttp
- **Database**: DataStore
- **Asynchronous Tasks**: Coroutines/Flow
- **Modularisation**: Yes
- **Testing**: JUnit

## 🚀 Getting Started

Follow these steps to run the project locally:

### Prerequisites

- **Android Studio** (version [specific version, if any])
- **Java Development Kit (JDK)** (version [specific version, if any])

### Development Setup
- **Run init script**
```shell
chmod +x scripts/init/project_init.sh
scripts/init/project_init.sh
```
- **Import Android Studio File Templates Settings from ideaSettings/file-templates-mvi-boilerplate.zip, this is for quickly generaete MVI code when create a feature with the boilerplate.**

### Run the app

1. **Clone the repository:**
    ```bash
    git clone https://github.com/crescent-hacker/dog-breed-quiz.git
    cd dog-breed-quiz
    ```
2. **Open in Android Studio:**
  - Launch Android Studio and select "Open an Existing Project".
  - Navigate to the cloned directory and select it.

3. **Build the project:**
  - Allow Android Studio to sync the Gradle files.
  - If prompted, download the required SDKs.

4. **Run the app:**
  - Select a device from the emulator list or connect a physical device.
  - Click on the "Run" button.

## 📋 TODO
  ### Feature
  - Score panel to keep track progress
  - Provide more different quiz types
  ### Engineering
  - Unit test
  - UI test



