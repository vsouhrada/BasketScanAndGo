rootProject.name = "BasketScanAndGo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal()
    }
}

includeBuild("build-logic")

include(":basketApp")
include(":composeApp")

include(":ktime")
project(":ktime").projectDir = file("platform/ktime")

include(":result")
project(":result").projectDir = file("platform/result")

include(":klogger")
project(":klogger").projectDir = file("platform/klogger")


include(":presentation")
project(":presentation").projectDir = file("core/logic/presentation")

include(":data")
project(":data").projectDir = file("core/logic/data")

include(":domain")
project(":domain").projectDir = file("core/logic/domain")

include(":designSystem")
project(":designSystem").projectDir = file("core/designSystem")

include(":di")
project(":di").projectDir = file("core/di")

//include(":androidApp")
//include(":shared")

include(":iosApp")
project(":iosApp").projectDir = file("iosApp")

include(":basket-server")
project(":basket-server").projectDir = file("server/basket-server")
