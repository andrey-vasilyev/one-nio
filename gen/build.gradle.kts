plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.license-conventions")
}

dependencies {
    api(libs.asm)

    implementation(project(":mgt"))
    implementation(libs.asm.util)
    implementation(libs.slf4j.api)
}