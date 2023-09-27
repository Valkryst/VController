[![Java CI with Maven](https://github.com/Valkryst/VController/actions/workflows/maven.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/maven.yml)
[![CodeQL](https://github.com/Valkryst/VController/actions/workflows/codeql.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/codeql.yml)

VController is a helper library for [JInput](https://github.com/jinput/jinput) with the following features:

* Automatic polling of controller input events VIA `ControllerPoller`.
* Automatic detection of controller connection and disconnection events VIA `HotSwapPoller`.
  * As JInput does not natively support hot-swapping, this library uses a polling approach to detect it. The downside to this approach is that JInput may print messages to `System.err` on every poll. 
* Interfaces to listen to controller and hot-swap events VIA `ControllerListener` and `HotSwapListener`.

This library assumes that you have already [properly configured](https://jinput.github.io/jinput/) JInput and that it is working.

## Table of Contents

* [Installation](https://github.com/Valkryst/VController#installation)
    * [Gradle](https://github.com/Valkryst/VController#-gradle)
    * [Maven](https://github.com/Valkryst/VController#-maven)
    * [sbt](https://github.com/Valkryst/VController#-scala-sbt)
* [Example](https://github.com/Valkryst/VController#example)
  * [Controller Polling](https://github.com/Valkryst/VController#controller-polling) 
  * [Hot Swap Polling](https://github.com/Valkryst/VController#hot-swap-polling) 

## Installation

VController is hosted on the [JitPack package repository](https://jitpack.io/#Valkryst/VController)
which supports Gradle, Maven, and sbt.

### ![Gradle](https://i.imgur.com/qtc6bXq.png?1) Gradle

Add JitPack to your `build.gradle` at the end of repositories.

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add VController as a dependency.

```
dependencies {
	implementation 'com.github.Valkryst:VController:2023.9.26'
}
```

### ![Maven](https://i.imgur.com/2TZzobp.png?1) Maven

Add JitPack as a repository.

``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add VController as a dependency.

```xml
<dependency>
    <groupId>com.github.Valkryst</groupId>
    <artifactId>VController</artifactId>
    <version>2023.9.26</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add VController as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "VController" % "2023.9.26"
```

## Example

### Controller Polling

This example prompts you to select a controller, then it continuously polls the controller for events and prints them to the console.

```java
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.Scanner;

public class ControllerExample {
    public static void main(String[] args) {
        final var controllerPoller = new ControllerPoller(getController());
        controllerPoller.addListener(event -> {
            final var component = event.getComponent();
            System.out.println(component.getName());
            System.out.println(component.getIdentifier());
            System.out.println(component.getDeadZone());
            System.out.println(component.getPollData());
            System.out.println();
        });
        controllerPoller.start(16);

        System.out.println("\nPolling for events. Try moving the controller's joysticks and pressing its buttons.");
    }

    private static ControllerEnvironment getEnvironment() {
        final var environment = ControllerEnvironment.getDefaultEnvironment();

        if (!environment.isSupported()) {
            throw new IllegalStateException("Controller environment is not supported.");
        }

        return environment;
    }

    private static Controller getController() {
        final var environment = getEnvironment();
        final var controllers = environment.getControllers();

        System.out.println("Detected Controllers");
        for (int i = 0 ; i < controllers.length ; i++) {
            System.out.println("\t" + i + ": " + controllers[i].getName());
        }

        System.out.print("\nEnter a number to select a controller: ");
        final var scanner = new Scanner(System.in);
        final var index = scanner.nextInt();
        System.out.println("\nYou selected: " + controllers[index].getName());

        return controllers[index];
    }
}
```

### Hot Swap Polling

This example prints a message to the console when a controller is connected or disconnected.

```java
import net.java.games.input.Controller;

public class HotSwapExample {
    public static void main(final String[] args) {
        final var hotswapPoller = HotSwapPoller.getInstance();
        hotswapPoller.addListener(new HotSwapListener() {
            @Override
            public void controllerAdded(Controller controller) {
                System.out.println("Added: " + controller.getName());
            }

            @Override
            public void controllerRemoved(Controller controller) {
                System.out.println("Removed: " + controller.getName());
            }
        });

        try {
            hotswapPoller.start();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        System.out.println("Polling for hot-swaps. Try adding or removing a controller.");
    }
}
```

## Credits & Inspiration

* [litiengine](https://github.com/gurkenlabs/litienginehttps://github.com/gurkenlabs/litiengine)