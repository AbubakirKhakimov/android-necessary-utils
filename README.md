### Gradle

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
