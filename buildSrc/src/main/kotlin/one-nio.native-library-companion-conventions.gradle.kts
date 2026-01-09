@file:Suppress("UnstableApiUsage")

plugins {
    `java-library`
}

val nativeLibraryProject = project.project(":${project.name}-native")
val nativeLibsDir = layout.buildDirectory.dir("native-libs")
sourceSets {
    main {
        resources {
            srcDir(nativeLibsDir)
        }
    }
}

tasks.register<Copy>("copyNativeLibraries") {
    dependsOn(nativeLibraryProject.tasks.assemble)
    from(nativeLibraryProject.layout.buildDirectory.dir("lib/main/debug"))
    into(nativeLibsDir)
}

tasks.processResources {
    dependsOn("copyNativeLibraries")
}

tasks.named("sourcesJar") {
    dependsOn("copyNativeLibraries")
}
