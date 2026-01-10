plugins {
    id("one-nio.java-library-conventions")
    id("one-nio.license-conventions")
}

dependencies {
    implementation(project(":async"))
    implementation(project(":lock"))
    implementation(project(":mgt"))
    implementation(project(":os"))
    implementation(project(":serial"))
    implementation(project(":util"))
    implementation(libs.slf4j.api)
}