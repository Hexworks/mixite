import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline val PluginDependenciesSpec.kotlinMultiplatform: PluginDependencySpec get() = kotlin("multiplatform")
inline val PluginDependenciesSpec.kotlinJvm: PluginDependencySpec get() = kotlin("jvm")
inline val PluginDependenciesSpec.kotlinJs: PluginDependencySpec get() = kotlin("js")