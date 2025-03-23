import org.gradle.api.JavaVersion

object Project {
    val sourceCompatibilityJava = JavaVersion.VERSION_17
    val targetCompatibilityJava = JavaVersion.VERSION_17

    const val GROUP_ID = "com.basket.sample.scango"
    const val GROUP_ID_PLATFORM = "com.basket.sample.scango.platform"
}

object BuildPlugins {

    const val mavenPublish = "maven-publish"
    const val javaLibrary = "java-library"
}

object Kotlin {

    object Jvm {
        const val JAVA_TARGET_VERSION = 17
    }
}

object AndroidSdk {
    const val min = 21
    const val compile = 33
    const val target = compile
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val namespace = Project.GROUP_ID
    const val namespaceModule = "$namespace.module"
}

object IOS {
    const val iosXSimulator = "iPhone 14"
}

