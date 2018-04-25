

## Links

* [JavaDoc Documentation](https://valkryst.github.io/VController/)

## Requirements

You *must* include the *.dll, *.so, and *.jnilib files from [here](http://ci.newdawnsoftware.com/job/JInput/lastBuild/artifact/dist/) along with your Jar file.

You *must* run your Jar with the `-Djava.library.path=folderName/` parameter, where folderName is the name of the folder that contains the *.dll, *.so, and *.jnilib files.


## Jar Files & Maven

The Maven dependency is hosted off of JitPack, so you will need to add JitPack as a repository before you add VController as a dependency.

### Maven

JitPack ([Example](https://github.com/Valkryst/VTerminal/blob/master/pom.xml)):

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

Dependency:

    <dependency>
        <groupId>com.github.Valkryst</groupId>
        <artifactId>VController</artifactId>
        <version>0.1.4-BETA</version>
    </dependency>

### Jar

Jar files can be found on the [releases](https://github.com/Valkryst/VController/releases) page.
