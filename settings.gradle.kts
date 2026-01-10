dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "one-nio"

include("async")
include("cluster")
include("compiler")
include("config")
include("gen")
include("lock")
include("lz4")
include("lz4-native")
include("mem")
include("mgt")
include("net")
include("net-native")
include("os")
include("os-native")
include("pool")
include("reflection")
include("reflection-native")
include("serial")
include("util")