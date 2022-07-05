# BlueLib
The opposite of RedLib. That's why it's good.

# Dependency Information
Use [jitpack](https://jitpack.io/) for using BlueLib as a dependency.

## Gradle
```gradle
plugins {
    id 'java'
    id 'com.github.johnrengelman.shadow' version '7.1.2'
}

repositories {
    maven {
        id = 'jitpack.io'
        url = 'https://jitpack.io/'
    }
}

shadowJar {
    include {
        dependencies {
            include('com.github.imajindevon:BlueLib:1.0.0')
        }
    }
}

dependencies {
    implementation 'com.github.imajindevon:BlueLib:main'
}
```

## Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io/</id>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <artifactId>BlueLib</artifactId>
        <groupId>com.github.ImajinDevon</groupId>
        <version>main</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```
