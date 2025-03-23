import com.basket.sample.scango.configureKotlinMultiplatformBase
import com.basket.sample.scango.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformBaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatformBase)
        //extensions.configure<LibraryExtension>(::configureKotlinJvm)
    }
}
