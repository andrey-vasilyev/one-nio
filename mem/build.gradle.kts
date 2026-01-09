plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.license-conventions")
}

dependencies {
    implementation(project(":lock"))
    implementation(project(":mgt"))
    implementation(project(":util"))
    implementation(libs.slf4j.api)
}