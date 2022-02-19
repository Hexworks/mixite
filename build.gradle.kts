allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}