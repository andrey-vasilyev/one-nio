plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.native-library-companion-conventions")
}

dependencies {
    implementation(project(":util"))
}