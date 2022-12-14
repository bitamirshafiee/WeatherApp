# WeatherApp

## How to Build the APP
in case you want to build the app you need the keystore.properties file which contains credentials
to be place in the WeatherApp(the main) folder otherwise it won't be built.

## About
This is an app getting your approximate location and telling you about the current weather condition.
These are the information you can get:

1.weather condition by an image
2.your city of location(this api that I am using show the capital of the country you are in,
so not the exact city)
3.current temperature
4.feels like temperature

## How to use the app

First when you open the app it will ask you for ACCESS_COARSE_LOCATION (an approximate location is
enough for us) and it gets lat and lon of your current location and get the current weather condition.

if you don't give location permission to app, it will show you proper message.
if you give the location permission but your location is off it will ask you turn to it on and continue
to be able to continue this feature.


## Project Structure

This app is written in kotlin, and below is what I used in this app:

1.MVVM architecture
2.Jetpack Compose
3.Dagger
4.Coroutine
5.Networking Libraries(Retrofit, OkHttp)
6.Unit Tests and UI Tests are written
7.CI Added by Circleci(We can go further by adding CD with fastlane)
