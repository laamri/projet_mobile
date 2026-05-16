# LAB 17 – Mastering BroadcastReceiver in Android

## Course

**Mobile Programming: Android with Java**

This lab focuses on understanding and mastering **BroadcastReceiver** in Android.
BroadcastReceivers allow Android applications to listen and react to system events or custom events sent by applications.

Examples of broadcasts:

* Airplane mode changes
* Battery status updates
* Device boot completed
* WiFi state changes
* SMS received
* Custom app events

---

# 🎯 Objectives of the Lab

By the end of this lab, we learned how to:

* Create and use **BroadcastReceivers**
* Understand the difference between:

  * **Dynamic Receivers**
  * **Static Receivers**
* Detect system events such as:

  * `ACTION_AIRPLANE_MODE_CHANGED`
  * `BOOT_COMPLETED`
* Send and receive **custom broadcasts**
* Work with:

  * `Intent`
  * `IntentFilter`
  * `registerReceiver()`
  * `unregisterReceiver()`
* Manage Android permissions in `AndroidManifest.xml`
* Understand Android security restrictions:

  * `android:exported`
  * Background execution limitations
  * Android 12/13/14/15/16 broadcast rules
* Handle receiver lifecycle correctly to avoid memory leaks

---

# demo video :



https://github.com/user-attachments/assets/857e6626-4991-49d7-b922-d49adf311b72








#  Project Overview

We created an Android application called:

## `ReceiverDemo`

The application demonstrates:

1. A **Dynamic BroadcastReceiver** for Airplane Mode changes
2. A **Static BroadcastReceiver** for device boot
3. A **Custom BroadcastReceiver** for app-defined events

---

# 📌 Step 1 – Create the Android Project

We started by creating a new Android Studio project.

### Main Components:

* `MainActivity`
* `AirplaneModeReceiver`
* `BootReceiver`
* `CustomEventReceiver`

---

# 📡 Step 2 – Dynamic BroadcastReceiver (Airplane Mode)

## 🔹 What We Learned

A **Dynamic Receiver** is registered while the application is running.

It works only when:

* The Activity is active
* The receiver is registered

This method is:

* More battery efficient
* Safer
* Recommended for modern Android versions

---

##  AirplaneModeReceiver.java

```java
public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {

            boolean isAirplaneOn = intent.getBooleanExtra("state", false);

            String message = isAirplaneOn
                    ? "Mode Avion ACTIVÉ"
                    : "Mode Avion DÉSACTIVÉ";

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
```

---

## Important Concepts

### `extends BroadcastReceiver`

Every receiver must inherit from `BroadcastReceiver`.

---

### `onReceive()`

Main method executed when a broadcast is received.

⚠️ Runs on the **main thread**, so heavy operations should be avoided.

---

### `Intent.ACTION_AIRPLANE_MODE_CHANGED`

System broadcast triggered when airplane mode changes.

---

### `getBooleanExtra("state", false)`

Retrieves the airplane mode state:

* `true` → enabled
* `false` → disabled

---

### `Toast`

Used to display quick messages for testing.

---

#  Step 3 – Static BroadcastReceiver (BOOT_COMPLETED)

## 🔹 What We Learned

A **Static Receiver** is declared in the `AndroidManifest.xml`.

It can work even when:

* The application is closed
* The Activity is not running

---

##  BootReceiver.java

```java
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            Toast.makeText(
                context,
                "Téléphone démarré !",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}
```

---

#  Step 4 – Manifest Configuration

## 🔹 Permissions

```xml
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

This permission allows the application to receive the boot completed event.

---

## 🔹 Receiver Declaration

```xml
<receiver
    android:name=".BootReceiver"
    android:exported="false">

    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
    </intent-filter>

</receiver>
```

---

##  What We Learned About Security

### `android:exported="false"`

Prevents other applications from sending broadcasts to our receiver.

Required since:

* Android 12+

Important for:

* Security
* Preventing unauthorized access

---

#  Step 5 – Managing Dynamic Receivers in MainActivity

## 🔹 Main Concepts Learned

We learned how to:

* Register receivers dynamically
* Unregister receivers
* Use `IntentFilter`
* Send custom broadcasts
* Manage receiver lifecycle safely

---

## 📄 MainActivity.java (Main Features)

### Registering the Receiver

```java
IntentFilter filter = new IntentFilter();
filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

registerReceiver(airplaneReceiver, filter);
```

---

### Unregistering the Receiver

```java
unregisterReceiver(airplaneReceiver);
```

Important to avoid:

* Memory leaks
* Crashes

---

### Sending a Custom Broadcast

```java
Intent intent = new Intent("com.example.receiverdemo.CUSTOM_EVENT");

intent.putExtra("message", "Bonjour depuis le custom broadcast !");

sendBroadcast(intent);
```

---

#  Step 6 – Custom BroadcastReceiver

## 🔹 What We Learned

Applications can create their own custom broadcasts.

This enables:

* Internal app communication
* Event-driven architecture
* Loose coupling between components

---

## 📄 CustomEventReceiver.java

```java
public class CustomEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("com.example.receiverdemo.CUSTOM_EVENT"
                .equals(intent.getAction())) {

            String message = intent.getStringExtra("message");

            Toast.makeText(
                context,
                "Custom reçu : " + message,
                Toast.LENGTH_LONG
            ).show();
        }
    }
}
```

---

##  Manifest Declaration

```xml
<receiver
    android:name=".CustomEventReceiver"
    android:exported="false"/>
```

---

#  Step 7 – Simple User Interface

##  activity_main.xml

```xml
<LinearLayout>

    <TextView
        android:id="@+id/tvStatus"
        android:text="Status"/>

    <Button
        android:id="@+id/btnToggleAirplane"
        android:text="Activer Receiver Avion"/>

    <Button
        android:id="@+id/btnSendCustom"
        android:text="Envoyer Custom Broadcast"/>

</LinearLayout>
```

---

#  Step 8 – Testing the Application

## Tests Performed

###  Dynamic Receiver Test

* Enable airplane mode
* Disable airplane mode
* Observe Toast messages

---

###  Static Receiver Test

* Restart the phone/emulator
* Observe boot completed message

---

###  Custom Broadcast Test

* Click button
* Send custom event
* Verify reception

---

#  Key Concepts Learned

## 🔹 Dynamic vs Static Receivers

| Dynamic Receiver              | Static Receiver                 |
| ----------------------------- | ------------------------------- |
| Registered in code            | Declared in Manifest            |
| Active only while app runs    | Can work when app closed        |
| Better for battery            | More persistent                 |
| Recommended in modern Android | Limited by Android restrictions |

---

#  Android Security & Modern Restrictions

We learned that modern Android versions impose restrictions on broadcasts to improve:

* Security
* Privacy
* Battery optimization

Important concepts:

* `android:exported`
* Background execution limits
* Restricted implicit broadcasts

---

# ⚠️ Best Practices Learned

##  Always unregister dynamic receivers

```java
@Override
protected void onDestroy() {
    unregisterReceiver(receiver);
}
```

Prevents:

* Memory leaks
* App crashes

---

##  Avoid heavy operations inside `onReceive()`

Because:

* Runs on main thread
* Can freeze the UI

Use:

* WorkManager
* Services
* Background threads

---

##  Prefer Dynamic Receivers when possible

Benefits:

* Better battery management
* More secure
* More efficient

---

##  Use explicit permissions carefully

Only request permissions when necessary.

---

#  Real-World Applications of BroadcastReceiver

BroadcastReceivers are used in:

* SMS applications
* Battery monitoring apps
* Network monitoring
* Security applications
* Alarm systems
* Background synchronization
* IoT communication

---

#  Conclusion

In this lab, we successfully learned how Android BroadcastReceivers work and how to implement them correctly using Java.

We explored:

* Dynamic receivers
* Static receivers
* Custom broadcasts
* Manifest permissions
* Android security restrictions
* Lifecycle management

This lab provides a strong foundation for understanding Android system events and event-driven application development.
