# cs-users-api

This projects has the following dependencies:

- Java 16
- MySql (installed via homebrew)
- Gradle
- IntelliJ (community edition should be enough maybe 🤔)
- FlyWay to handle the migrations

# Java 16

You can download the installer from the [Oracle Archive page](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html#license-lightbox) 

# MySql

```
brew install mysql
```

## Launch mysql

```
mysql.server start 
```

## Stop mysql

```
mysql.server stop 
```

# Gradle

This project contains a wrapper provided via ``gradlew``

# Migrations

Migrations have to follow this naming convention to use FlyWay:

![img.png](img.png)

## Run

```
gradle flywayMigrate -i
```

## Clean

```
gradle flywayClear -i
```

# Run the application

```
./gradlew bootRun
```

# Package the application to a jar and run that generated jar file

```
./gradlew bootJar
java -jar build/libs/users-api-0.0.1-SNAPSHOT.jar
```

# Debug the application

```
 ./gradlew bootRun --debug-jvm
```

Then add a remote debugger on IntelliJ:

1. Go to Run Menu
2. Go to Debug
3. Edit configurations

![img_1.png](img_1.png)

![img_2.png](img_2.png)

![img_3.png](img_3.png)

