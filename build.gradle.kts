allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        kotlinx()
        kotlinEap()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}