# Munchi
An app for managing recipes<br/><br/>

This app was made using Android Studio 2022.3.1. In order to install, first run<br/>
`export ANDROID_HOME=[...]/Android/Sdk`<br/>
where `[...]` is the directory where the Android SDK was installed, most likely the home directory. 
Next, connect your Android device (virtual or physical) inside the project directory, run<br/>
`./gradlew installDebug`<br/>
on Linux, or<br>
`gradlew.bat installDebug`<br/>
on Windows. If you receive an error that no device is connected, an .apk should still be generated in<br/>
`[Project dir]/app/build/outputs/apk/debug`<br/>
which can be copied and installed on an Android device.<br/><br/>

The entire application uses one Activity that switches between several Fragments. This was done so that<br/>
- nav_graph can be used easily for navigating through pages and communicating data, such as search queries and recipe IDs, between them.
- Objects that all Fragments need access to, such as the database handler, can be stored as an attribute of the main Activity rather than the application.
- The scope of the project did not require more advanced features, such as menus or UI overlays.
<br/>
The app uses a simple SQLite database for storing data. Since it is a standalone app and there is no "sensitive" data, storing the database locally 
on the device is sufficient. SQLite can also be handled with the SQLiteOpenHelper framework.<br/><br/>

Some features that still need to be implemented:
- Recipes cannot be added or edited. This requires for the ingredients and preparation steps to be extracted from individual TextEdit views inside a RecyclerView. Since elements would have to be added and removed freely from this Recyclerview, the difficulty is constantly linking it up with an array accessible to the adapter.
- Searching recipes can only be done on terms occurring in the instructions and filtered based on vegetarian recipes. The framework exists for defining a range of how many people the recipe serves and which ingredients are included or excluded, but these have no influence on searching yet.
- If a large number of recipes are stored, rather than loading them all the moment the app is started up, they should appear in groups (e.g. of 10 recipes at a time) which are expanded each time the bottom of the scrollable view is reached.
- The testing button, which adds some default recipes to the database, should eventually be removed.





