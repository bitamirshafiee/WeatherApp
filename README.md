# WeatherApp

## How to Build the APP
In case you want to build the app you need the keystore.properties file which contains credentials
to be place in the WeatherApp(the main) folder otherwise it won't be built.

## About
WeatherApp gets your approximate location and tells you about the current weather condition.
These are the information you can get:
1. **Weather Condition** by an appropriate image
2. Your **City of Location**(this api that I am using show the capital of the country you are in,
so not the exact city)
3. **Current temperature** of the user location
4. **Feels Like** temperature

## How to use the app

First when you open the app it will ask you for ACCESS_COARSE_LOCATION (an approximate location is
enough for us) and it gets *lat* and *lon* of your current location and get the current weather condition.

- If you don't give location permission to the app, it will show you proper message.
- If you give the location permission but your location is off it will ask you to turn it on,
to be able to continue with this feature.


## Project Structure

This app is written in kotlin, and below is what I used in this app:

- MVVM architecture
- Jetpack Compose
- Dagger
- Coroutine
- Networking Libraries(Retrofit, OkHttp)
- Unit Tests and UI Tests are written
- CI Added by Circleci(We can go further by adding CD with fastlane)
