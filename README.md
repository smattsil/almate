<p align="center">
   <a href="https://github.com/smattsil/almate/releases/download/v0.0.2/app-release.apk">
      <img src="screenshots/almatewhite.png" alt="almate logo" width=50% />
   </a>
</p>
<p align="center">
  <a href="https://stats.uptimerobot.com/0HCIzTy1EG/">
    <img src="https://img.shields.io/uptimerobot/status/m797562430-1dd4c1addad4402b1e688c4d" alt="UptimeRobot Badge">
  </a>
</p>

# Almate

Almate is an unofficial interpretation of how the alma (SIS) native android app should look, built with [Jetpack Compose][compose]. It works by web scraping the alma website, using a user's credentials. User credentials are NOT stored on any database that belongs to Almate, but rather on users' devices locally.

To try out this app, either download it, or use the latest stable version
of [Android Studio](https://developer.android.com/studio) to compile it.
You can clone this repository or import the
project from Android Studio following the steps
[here](https://developer.android.com/jetpack/compose/setup#sample).

Features:
* GPA calculator
* Ranking system

Features to come:
* AI chatbot (with Gemini for Android)
* AI grade predictor
* Final grade calculator

## Screenshots
<img src="screenshots/home.png" alt="home screen" height="600px" /> <img src="screenshots/rankings.png" alt="rankings screen" height="600px" /> <img src="screenshots/tools.png" alt="tools screen" height="600px" />

## Libraries
Here are a list of the libraries/technologies that were used (including for the API):

Kotlin
- Retrofit (for API requests)
- Hilt (for dependency injection)
- Coil (for loading images from the internet)
- Ktor (for the supabase database)
- Shimmer (for the shimmer effect)

Python
- FastAPI (api framework)
- asyncio (for asynchronous programming)
- aiohttp + selectolax (for web scraping)

## License
This project is under no license. Please feel free to message @smattsil on IG if you have any problems or legal issues you would like to address.

[compose]: https://developer.android.com/jetpack/compose
