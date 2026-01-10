plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.license-conventions")
    id("one-nio.native-library-companion-conventions")
}

dependencies {
    implementation(project(":mgt"))
    implementation(project(":util"))
    implementation(libs.slf4j.api)
}