[![Java CI with Maven](https://github.com/Valkryst/VController/actions/workflows/maven.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/maven.yml)
[![CodeQL](https://github.com/Valkryst/VController/actions/workflows/codeql.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/codeql.yml)

VController is a helper library for [JInput](https://github.com/jinput/jinput) which makes it easy to automatically
poll for controller input and trigger events when input is detected.

This library assumes that you have already [properly configured](https://jinput.github.io/jinput/) JInput and that it is working.

## Table of Contents

* [Installation](https://github.com/Valkryst/VController#installation)
    * [Gradle](https://github.com/Valkryst/VController#-gradle)
    * [Maven](https://github.com/Valkryst/VController#-maven)
    * [sbt](https://github.com/Valkryst/VController#-scala-sbt)
* [Example](https://github.com/Valkryst/VController#example)

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

This rudimentary example will prompt you to select a controller, poll it for events, and print any events to the console.

```java
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.Scanner;

public class Example {
    public static void main(String[] args) {
        final var poller = new ControllerPoller(getController());
        poller.addListener(event -> {
            final var component = event.getComponent();
            System.out.println(component.getName());
            System.out.println(component.getIdentifier());
            System.out.println(component.getDeadZone());
            System.out.println(component.getPollData());
            System.out.println();
        });
        poller.start(16);

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
