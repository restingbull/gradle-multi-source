import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.restingbull"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    create("public") {
        allSource.srcDir("src/public")
    }
    create("impl") {
        allSource.srcDir("src/impl")
    }
}


configurations {
    getByName(sourceSets["public"].compileClasspathConfigurationName) {
        isCanBeConsumed = true
    }

    getByName(sourceSets["main"].compileClasspathConfigurationName) {
        extendsFrom(getByName(sourceSets["public"].compileClasspathConfigurationName))
    }

    // make inaccessible to external calls
    getByName(sourceSets["impl"].compileClasspathConfigurationName) {
        isCanBeConsumed = false
    }
    getByName(sourceSets["impl"].runtimeClasspathConfigurationName) {
        isCanBeConsumed = false
    }
}

dependencies {
    testImplementation(kotlin("test"))
    add("implImplementation",sourceSets["public"].output.classesDirs)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}