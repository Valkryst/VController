[![Java CI with Maven](https://github.com/Valkryst/VController/actions/workflows/maven.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/maven.yml)
[![CodeQL](https://github.com/Valkryst/VController/actions/workflows/codeql.yml/badge.svg)](https://github.com/Valkryst/VController/actions/workflows/codeql.yml)

## Table of Contents

* [Installation](https://github.com/Valkryst/VController#installation)
    * [Gradle](https://github.com/Valkryst/VController#-gradle)
    * [Maven](https://github.com/Valkryst/VController#-maven)
    * [sbt](https://github.com/Valkryst/VController#-scala-sbt)

## Requirements

You *must* include the *.dll, *.so, and *.jnilib files from [here](http://ci.newdawnsoftware.com/job/JInput/lastBuild/artifact/dist/) along with your Jar file.

You *must* run your Jar with the `-Djava.library.path=folderName/` parameter, where folderName is the name of the folder that contains the *.dll, *.so, and *.jnilib files.

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
	implementation 'com.github.Valkryst:VController:0.1.4-BETA'
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
    <version>0.1.4-BETA</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add VController as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "VController" % "0.1.4-BETA"