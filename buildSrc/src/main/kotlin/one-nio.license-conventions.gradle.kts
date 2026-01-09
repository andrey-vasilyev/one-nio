plugins {
    id("dev.yumi.gradle.licenser")
}

license {
    include("**/*.c")
    include("**/*.java")
    rule(rootProject.file("COPYRIGHT_HEADER.txt"))
}

