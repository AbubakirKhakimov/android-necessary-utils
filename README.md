<h1 align="center">Android necessary utils library</a> 

<h3 align="center">Utils that facilitate the work of an Android developer.</h3>

## What does the library consist of

- ConnectivityManager -> A class that handles errors when loading data through Retrofit!

- GlobalFunctions -> Functions that make your code beautiful and save you time by making your job easier!

- LocaleManager -> Convenient class for changing, saving and restoring the application language!

- ThemeManager -> Convenient class for changing, saving and restoring the application theme!

- Result -> A handy class for sending an error or success over a single channel!

- LoadingDialogManager -> 

## Gradle

1. Add it in your root `build.gradle` at the end of repositories:

    ```javascript
    allprojects {
    	repositories {
    		...
    		maven { url "https://jitpack.io" }
    	}
    }
    ```

2. Add the dependency in your `app/build.gradle`:

    ```javascript
    dependencies {
        implementation 'com.github.AbubakirKhakimov:android-necessary-utils:1.0'
    }
    ```
