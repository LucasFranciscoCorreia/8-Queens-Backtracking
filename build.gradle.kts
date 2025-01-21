import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id 'application'
    id 'org.jetbrains.kotlin.jvm' version '2.1.0'
    id 'org.javamodularity.moduleplugin' version '1.8.15'
    id 'org.openjfx.javafxplugin' version '0.1.0'
    id "org.beryx.jlink" version "3.1.1"
}

group = 'br.ufrpe'
version = '1.0-SNAPSHOT'

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDir = compileKotlin.destinationDir

application {
    mainModule.set("br.ufrpe.n8queens")
    mainClass.set("br.ufrpe.n8queens.NQueensUIKt")
}

repositories {
    mavenCentral()
}

javafx {
    version.set(23)
    modules = listOf("javafx.controls", "javafx.fxml")
}

jlink{
    launcher {
        name = "hello"
    }
    imageZip.set(project.file("${project.buildDir}/image-zip/hello-image.zip"))
}