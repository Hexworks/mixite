enableFeaturePreview("GRADLE_METADATA")

rootProject.name = "mixite"

pluginManagement {
    repositories {
        maven("https://kotlin.bintray.com/kotlinx")
        gradlePluginPortal()
    }
}

include(":mixite.core")
//include(":mixite.benchmarks")