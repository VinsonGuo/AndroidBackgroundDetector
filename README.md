# AndroidBackgroundDetector
A detector for front-to-back switching of android apps

### Usage:

#### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2. Add the dependency

```groovy
	dependencies {
        implementation 'com.github.VinsonGuo:AndroidBackgroundDetector:1.0.0'
	}
```

#### Step 3. Add the listener
```kotlin
    application.addBackgroundDetectListener {
        Log.d("BackgroundDetector", "App is in background $it")
        // do something
    }
```