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

## What Was Improved Compared to the Original Lab

- Removed the stray `HLJSTAGSAFE1X` text that breaks compilation.
- Fixed service stopping logic by sending explicit actions to the service instead of relying on `stopService(...)` to pass commands.
- Added real Activity-to-Service synchronization with a listener registered through the binder.
- Improved timer reliability by calculating elapsed time from system uptime.
- Added richer notification actions so the chronometer can be controlled without reopening the app.
- Upgraded the basic screen into a cleaner, more polished stopwatch-style interface.

## Demo Video

Add your demo link here once the recording is uploaded:

- **YouTube / Drive / LinkedIn demo**: `PASTE_VIDEO_LINK_HERE`

Suggested video flow:

1. Launch the app.
2. Start the chronometer.
3. Put the app in the background and show the persistent notification.
4. Pause and resume from the notification.
5. Reopen the app and show that the UI is still synchronized.
6. Reset and stop the service.

## Screenshots

If you want, you can add screenshots under a `screenshots/` folder and reference them here:

```md
![Main Screen](screenshots/main-screen.png)
![Notification Controls](screenshots/notification.png)
```

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
