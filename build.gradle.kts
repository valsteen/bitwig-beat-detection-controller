import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.languageVersion = "1.6"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.bitwig.com")
    maven("https://repo.maven.apache.org/maven2")
}

dependencies {
    api(libs.com.bitwig.extension.api)
    api(libs.org.jetbrains.kotlin.kotlin.stdlib)
    api(libs.org.jetbrains.kotlin.kotlin.stdlib.common)
}

group = "beatdetection"
version = "1.0.0"
description = "Beat detection controller"


tasks.named<Jar>("jar").configure {
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    manifest {
        attributes["Main-Class"] = "beatdetection.beatdetection.BeatDetectionController"
    }
}

tasks.register<Copy>("copyJar") {
    val userHome = System.getProperty("user.home")
    val targetDirectory = "$userHome/Documents/Bitwig Studio/Extensions"

    from(tasks.jar.get())
    into(targetDirectory)
    rename { fileName ->
        val newName = "BeatDetection.bwextension"
        println("Renaming $fileName to $newName")
        newName
    }
    dependsOn(tasks.jar)


    doFirst {
        println("Copying to directory: $targetDirectory")
    }

    eachFile {
        println("Processing file: $file")
    }

    // Execute after the task actions
    doLast {
        println("Copy completed to directory: $targetDirectory")
    }
}


dependencies {
    testImplementation("org.mockito:mockito-core:4.1.0")
    testImplementation("org.mockito:mockito-inline:4.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}
tasks.test {
    useJUnitPlatform()
}