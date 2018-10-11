
An example of how to use Malai to develop a JavaFX applications.
This app follows the MVP pattern: the presenters (package 'instrument') binds the model to the view. These two last do not know each others.
If you move this bindings to the view, this becomes an MVC pattern.

### Packages

'draw': the main class.
'command': the GUI commands that users can produce while interacting with the GUI.
'instrument': the controllers/presenters/... that receives events from the GUI and that transform them into commands
'model': the model of the app. Knows nothing about the view, the instruments, and the commands
'view': the JavaFX view

### Requirements

Java 11 is required.

### Build

`mvn clean package` does the job.

### execution

The `dist` folder is the packaged app. 
No JVM is required to run the app since `dist` contains both the app and a tiny JVM dedicated to the app.
Double-click on `dist/bin/run` to launch the app.