# PhotoApp

## Project Description
Simple app with infinite list to show the images taken by the camera stored in a database.
- The app was made with a single activity *without* fragments.
- App made using clean architecture in a modularized project.
- The app don't request to user the permissions of camera and write/read storage.
- The app supports the light and dark theme.
- Managing errors.

## Libraries used
- Dagger Hilt: For dependency injection.
- Coroutines: To retrieve data from the database outside the UI Thread.
- LiveData: According observer pattern used to get noticed changes from view models on the views.
- Coil: Retrieve the images from the file storage.
- Architecture Components:
  - Compose: Build the views with it.
  - Room: Creation and handle the database.
  - ViewModels: At the presentation layer.
- JUnit: On the unit tests execution.
- Mockito: To mock instances on unit test processing.
