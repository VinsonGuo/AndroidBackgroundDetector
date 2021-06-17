# AndroidBackgroundDetector
A detector for front-to-back switching of android apps

Usage:

```kotlin
    application.addBackgroundDetectListener{
        Log.d("BackgroundDetector", "App is in background $it")
        // do something
    }
```