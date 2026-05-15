# Android Chronometer Service - LAB 16

A robust Android application in Java that demonstrates how to build a **Foreground Service** and connect it to an Activity through a **Bound Service** pattern. This project extends the original lab by improving timing accuracy, notification controls, and UI synchronization.

## Key Featuresa

- **Foreground Service**: Keeps the chronometer active while the app is in the background.
- **Bound Service Communication**: The Activity stays synchronized with the running service through a local `Binder`.
- **Interactive Notification Controls**:
    - Start / Resume
    - Pause
    - Reset
    - Stop
- **Accurate Time Tracking**: Uses `SystemClock.elapsedRealtime()` instead of a simple counter to reduce drift.
- **Live UI Updates**: The main screen reflects the current service state in real time.
- **Android 14 Ready**: Includes foreground service declarations and notification permission handling for modern Android versions.


## Demo Video


https://github.com/user-attachments/assets/99dd913e-9a19-4793-b8ab-8a6c91c9d569

## What Was Improved Compared to the Original Lab

- Removed the stray `HLJSTAGSAFE1X` text that breaks compilation.
- Fixed service stopping logic by sending explicit actions to the service instead of relying on `stopService(...)` to pass commands.
- Added real Activity-to-Service synchronization with a listener registered through the binder.
- Improved timer reliability by calculating elapsed time from system uptime.
- Added richer notification actions so the chronometer can be controlled without reopening the app.
- Upgraded the basic screen into a cleaner, more polished stopwatch-style interface.




## Tech Stack

- **Language**: Java
- **Architecture**: Foreground Service + Bound Service
- **UI**: XML layouts with custom drawables
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Compile SDK**: 34
- **Java Version**: 17

## Project Structure

- `app/src/main/java/com/example/servicechronometrejava/ChronometreService.java`
  Main foreground service, notification actions, timer logic, and binder access.
- `app/src/main/java/com/example/servicechronometrejava/MainActivity.java`
  Main UI, permission handling, service binding, and state rendering.
- `app/src/main/AndroidManifest.xml`
  Service declaration and required permissions.
- `app/src/main/res/layout/activity_main.xml`
  Stopwatch screen layout.

## Permissions Used

- `android.permission.FOREGROUND_SERVICE`
- `android.permission.FOREGROUND_SERVICE_SPECIAL_USE`
- `android.permission.POST_NOTIFICATIONS`

Note: On Android 13+ the notification permission must be granted at runtime before the foreground notification can be shown normally.

## How It Works

1. The user presses **Start**.
2. `MainActivity` sends an action to `ChronometreService`.
3. The service enters foreground mode and shows a persistent notification.
4. The timer updates every second while elapsed time is computed from `SystemClock.elapsedRealtime()`.
5. The Activity binds to the service and receives live updates through the local binder listener.
6. The user can pause, reset, or stop either from the app or directly from the notification.

## How to Run

1. Clone this repository.
2. Open the project in **Android Studio**.
3. Sync Gradle.
4. Run the `:app` module on an emulator or a physical device.
5. Grant the notification permission when prompted on Android 13+.

## Learning Goals

This lab helps you understand:

- The lifecycle of an Android `Service`
- The difference between **started** and **bound** services
- Why **foreground services** require persistent notifications
- How to communicate between an `Activity` and a `Service`
- How to handle modern Android background execution restrictions


## Main Concepts Learned in This Lab

This lab mainly teaches how Android **Services** work, especially when an app needs to continue running in the background.

- **Foreground Service**  
  A service that keeps running in the background while showing a persistent notification. This is required for long-running visible tasks on modern Android.

- **Bound Service**  
  A service that an `Activity` can connect to using a `Binder` in order to exchange data or call methods directly.

- **Service Lifecycle**  
  The lab introduces the main lifecycle methods of a service:
  - `onCreate()` for initialization
  - `onStartCommand()` to receive actions and start work
  - `onBind()` to allow connection from an Activity
  - `onDestroy()` to clean up resources

- **Foreground Notification**  
  A foreground service must show a notification so the user knows the background task is active.

- **Binder Communication**  
  The `Binder` allows the Activity to get access to the service instance and stay synchronized with it.

- **Background Timing Task**  
  The service runs repeated work in the background to update the chronometer over time.

- **Manifest and Permissions**  
  The lab also teaches that services must be declared in `AndroidManifest.xml`, and that modern Android requires permissions such as notification permission for proper behavior.

- **Modern Android Restrictions**  
  The project shows that background execution is more restricted in recent Android versions, so foreground services and correct declarations are now essential.


## Current Scope

This version currently supports:

- Start / Resume
- Pause
- Reset
- Stop
- Background execution with notification controls

It does **not** currently include lap history or `InboxStyle` lap previews in the notification.

---



Developed for educational purposes as part of **LAB 16 - Mastering Services in an Android Application**.
