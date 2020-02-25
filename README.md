
![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.inria.fr%2Fmalai%2Fjob%2Finteracto-javafx&style=for-the-badge)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco?jobUrl=https%3A%2F%2Fci.inria.fr%2Fmalai%2Fjob%2Finteracto-javafx&style=for-the-badge) 
![GitHub](https://img.shields.io/github/license/interacto/interacto-javafx?style=for-the-badge)
![Maven Central](https://img.shields.io/maven-central/v/io.github.interacto/interacto-javafx?style=for-the-badge)
![java](https://img.shields.io/badge/java-11-green?style=for-the-badge)



# Interacto JavaFX

The JavaFX binding of the Interacto library.

Interacto is a UI library that eases the processing of user interface events.
A developer uses the Interacto fluent API to configure how a selected user interaction is transformed into a (undoable) command.

## Examples

Following a serie of examples. For each example, the first code chunk is the JavaFX code that uses standard UI events.
The second is the equivalent Interacto code.
 

- A very basic example. I want to undo the latest command by clicking on the undo button.

```java
undoButton.setOnAction(evt -> {
    // do the undo job here
});
```


```java
buttonBinder()
    .toProduce(Undo::new)
    .on(undoButton)
    .bind();
```

What differences?

You do not use UI event and listeners but pre-defined user interactions (provided by `buttonBinder` here).
In this case, the benefits is not direct as a button interaction is quite simple (it involves a single UI event).

You define commands (the `Undo` class here): classes that will do the job, and you can also add undo/redo features in your commands. 
Defining commands as classes have several benefits here:
your UI processing code is less complex as the command bahevior is defined in a specific class;
you can easily make your commands undoable;
you can access and manage the history of executed commands;
you factorise your code as commands may be executed in several places in your code. 




- I want to delete a selected object by pressing on the key `DELETE`.

```java
canvas.addEventHandler(KeyEvent.KEY_TYPED, evt -> {
    if(evt.getCode() == KeyCode.DELETE) {
        // do the deletion job here
    }
});
```


```java
shortcutBinder()
    .toProduce(() -> new DeleteShapes(canvas.getSelection()))
    .on(canvas)
    .with(KeyCode.DELETE)
    .bind();
```

Instead of having an `if` statement checking the key, you can use the routine `with` that will do the job for you.
So it reduces the cyclomatic complexity.



- I want to drag-and-drop (DnD) the selected color of a color picker onto an object to change its color.

A much more complex example in vanilla JavaFX.

```java
lineCol.addEventHandler(MouseEvent.MOUSE_DRAGGED, evt -> {
    lineCol.getScene().setCursor(new ColorCursor(lineCol.getValue()));
});

lineCol.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> {
    if(evt.getPickResult().getIntersectedNode() instanceof Shape) {
        // do the command here
    }
    lineCol.getScene().setCursor(Cursor.DEFAULT);
});
```

With this example, the Interacto code is not less verbose but much more direct and avoids the use of spaghetti of callbacks.

```java
nodeBinder()
    .usingInteraction(DnD::new)
    .toProduce(i -> new ChangeColour(lineCol.getValue(), null))
    .on(colorPicker)
    .first(i -> lineCol.getScene().setCursor(new ColorCursor(lineCol.getValue())))
    .then((i, c) -> i.getTgtObject().map(view -> (MyShape) view.getUserData()).ifPresent(sh -> c.setShape(sh)))
    .when(i -> i.getTgtObject().orElse(null) instanceof Shape)
    .endOrCancel(i -> colorPicker.getScene().setCursor(Cursor.DEFAULT))
    .bind();
```

with explanations:

```java
nodeBinder()
    // I want to use a built-in DnD interaction
    .usingInteraction(DnD::new)
    // to produce as output a command that will change the color of the targeted object
    .toProduce(i -> new ChangeColour(lineCol.getValue(), null))
    // the DnD operates on the color picker
    .on(colorPicker)
    // at the beginning of the DnD I change the cursor of the app
    .first(i -> lineCol.getScene().setCursor(new ColorCursor(lineCol.getValue())))
    // on each update of the ongoing DnD I look at the current targeted object
    .then((i, c) -> i.getTgtObject().map(view -> (MyShape) view.getUserData()).ifPresent(sh -> c.setShape(sh)))
    // 'When' is a predicate that constraints the execution of the command.
    // I want to change the color of a Shape object only
    .when(i -> i.getTgtObject().orElse(null) instanceof Shape)
    // when the DnD ends or is cancelled, I set the default cursor
    .endOrCancel(i -> colorPicker.getScene().setCursor(Cursor.DEFAULT))
    // I build the widget binding
    .bind();
```