import com.basket.sample.scango.libs
import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("androidApplication").get().get().pluginId)
            apply(libs.findPlugin("jetbrainsCompose").get().get().pluginId)
            apply(libs.findPlugin("compose.compiler").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
            apply(libs.findPlugin("kotlin.parcelize").get().get().pluginId)
        }
    }
}
