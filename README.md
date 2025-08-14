## TMDB Movie App
This app demonstrates modern Android development with Jetpack Compose, Hilt, Coroutines, Flow, Jetpack, and Material Design based on MVVM architecture. it showcases movies from [TMDB Api](https://developer.themoviedb.org/docs/getting-started) and also include tests with Mockk.

## Screenshots & Video 🎬 📷
| Home Screen  | Detail Screen | Trailer Screen |
| :---: | :---: | :---: |
|<img alt="home" src="https://github.com/user-attachments/assets/471e0c8a-e976-43c6-9303-daa60ba8eb18" />  | <img alt="details" src="https://github.com/user-attachments/assets/f59583c1-32aa-40a4-9a1a-67c365c94391" /> | <img alt="trailer" src="https://github.com/user-attachments/assets/58086d9c-11b2-4798-82be-44cc683bb9ca" />

## Technologies used 

- Jetpack Compose
- Dagger-Hilt
- Retrofit
- Pagging 3
- MVVM Architecture
- Navigation 3
- Mockk
- Gradle Version catalogue

## Modularization

Movie App implements the feature module strategy. It contains the following types of modules:

- The `app` module - contains app level and scaffolding classes that bind the rest of the codebase, such as MainActivity, `MovieAppNavigation` that controlls navigation using Naviagtion 3 library
- The `feature` module - feature specific modules which are scoped to handle a single responsibility in the app.
- The `core` module - common library modules containing auxiliary code shared between other modules in the app. It contains the following modules : `data`, `domain`, `network` and `model`

## How to Run 🔧

- Clone the project
- Open the project in Android Studio.
- Add your TMDB token in the `local.properties` file
 ```
TMDB_API_TOKEN = "YOUR_TOKEN_HERE"
```
- Build and run the app on an Android emulator or device.

## Contributing 🤝
Feel free to submit pull requests or open issues to improve the app. 
