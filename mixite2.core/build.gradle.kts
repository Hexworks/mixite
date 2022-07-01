import Libs.cobaltDatatypes

plugins {
    kotlinMultiplatform
}

group = "org.hexworks.mixite2"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(cobaltDatatypes)
            }
        }
    }
    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    js {
    }

    dependencies {

        with(Libs) {
            commonMainApi(kotlinStdLibCommon)
            commonMainApi(cobaltDatatypes)

            jvmMainApi(kotlinStdLibJdk8)

            jsMainApi(kotlinStdLibJs)
        }

        with(TestLibs) {
            commonTestApi(kotlinTestCommon)
            commonTestApi(kotlinTestAnnotationsCommon)

            jvmTestApi(kotlinTestJunit)

            jsTestApi(kotlinTestJs)
        }
    }
}