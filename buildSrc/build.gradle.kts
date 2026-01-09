plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-gradle-plugin", version = "2.3.0")
    implementation(group = "dev.yumi.gradle.licenser", name = "dev.yumi.gradle.licenser.gradle.plugin", version = "2.2.1")
    implementation(group = "org.jreleaser", name = "org.jreleaser.gradle.plugin", version ="1.22.0")
}