plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.license-conventions")
}

dependencies {
    implementation(project(":gen"))
    implementation(libs.asm)
    implementation(libs.slf4j.api)
}