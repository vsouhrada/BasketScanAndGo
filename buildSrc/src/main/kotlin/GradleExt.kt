import java.io.ByteArrayOutputStream
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.provider.Property

inline fun <reified T> Project.findPropertyOrNull(name: String): T? = findProperty(name) as T?

inline fun <reified T> Project.getProperty(name: String): T = property(name) as T

inline fun <reified T> Project.getPropertyOrDefaultValue(propertyName: String, defaultValue: Any) =
    findPropertyOrNull(propertyName)
        ?: defaultValue

/**
 * Gets number of commits in projects repository.
 *
 * @param paths Commits modifying the given [paths] are selected.
 *
 * @return Number of commits in projects repository.
 */
fun Project.getGitCommits(vararg paths: String): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = if (paths.isNotEmpty()) {
            listOf("git", "rev-list", "--count", "HEAD", "--", paths.joinToString(separator = " "))
        } else {
            listOf("git", "rev-list", "--count", "HEAD")
        }
        standardOutput = stdout
    }
    return try {
        Integer.parseInt(stdout.toString().trim())
    } catch (ex: NumberFormatException) {
        0
    }
}

/**
 * Find out if build is tagged as snapshot or not.
 * @return true if build is tagged as snapshot.
 */
fun Project.isSnapshot(): Boolean {
    return if (project.hasProperty("version")) {
        project.property("version").toString().endsWith("-SNAPSHOT")
    } else {
        false
    }
}

fun Project.isMacOs(): Boolean {
    return Os.isFamily(Os.FAMILY_MAC)
}

infix fun <T> Property<T>.by(value: T) {
    set(value)
}
