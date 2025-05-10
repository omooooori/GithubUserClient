# GitHub User Client

A Android application that allows users to search and view GitHub user profiles using the GitHub API.

## Screenshots

### User List Screen
![User List Screen Light Mode](docs/capture/user_list_screen_light_mode.png)
![User List Screen Dark Mode](docs/capture/user_list_screen_dark_mode.png)

### User Detail Screen
![User Detail Screen Light Mode](docs/capture/user_detail_screen_light_mode.png)
![User Detail Screen Dark Mode](docs/capture/user_detail_screen_dark_mode.png)

### Foldable Layout
![Foldable Layout](docs/capture/foldable_layout.png)

## Project Structure

```
app/                  # Application module
├── src/main/         # Main source code
│   ├── java/         # Kotlin source files
│   └── res/          # Resources
└── src/test/         # Unit tests

data/                 # Data layer
├── src/main/         # Repository and API implementation
└── src/test/         # Repository tests

domain/               # Domain layer
├── src/main/         # Use cases
└── src/test/         # Use case tests

feature-userlist/     # User list feature
├── src/main/         # Screen and ViewModel
└── src/test/         # Feature tests

feature-userdetail/   # User detail feature
├── src/main/         # Screen and ViewModel
└── src/test/         # Feature tests

design/               # UI components
└── src/main/         # Reusable components and theme

model/                # Data models
└── src/main/         # Domain models

docs/                 # Documentation
├── component-diagram.puml
├── sequence-diagram.puml
└── capture/          # App screenshots
    ├── user_list_screen_light_mode.png
    ├── user_list_screen_dark_mode.png
    ├── user_detail_screen_light_mode.png
    ├── user_detail_screen_dark_mode.png
    └── foldable_layout.png
```

## Features

- Search GitHub users with real-time filtering
- View detailed user profiles including:
  - Basic information (username, avatar)
  - Company and location
  - Repository count
  - Follower/Following counts
  - Recent activity
- Material 3 design implementation
- Dark mode support
- Error handling with user-friendly messages
- Paging support for user list

## Architecture

The application follows Clean Architecture principles with the following layers:

- **Presentation Layer**: Screens and ViewModels
- **Domain Layer**: Use cases and business logic
- **Data Layer**: Repository and API implementation
- **Model Layer**: Data models
- **Design Layer**: Reusable UI components

## Technologies

- Kotlin
- Jetpack Compose
- Material 3
- Paging 3
- Koin for dependency injection
- Retrofit for API calls
- Coil for image loading
- JUnit and Espresso for testing 