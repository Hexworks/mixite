allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}