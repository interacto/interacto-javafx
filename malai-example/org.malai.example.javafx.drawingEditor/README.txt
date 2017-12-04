
An example of how to use Malai to develop a JavaFX applications.
This app follows the MVP pattern: the presenters (package 'instrument') binds the model to the view. These two last do not know each others.
If you move this bindings to the view, this becomes an MVC pattern.

Packages:

'draw': the main class.
'action': the GUI commands (the actions) that users can produce while interacting with the GUI.
'instrument': the controllers/presenters/... that receives events from the GUI and that transform them into actions
'model': the model of the app. Knows nothing about the view, the instruments, and the actions
'view': the JavaFX view