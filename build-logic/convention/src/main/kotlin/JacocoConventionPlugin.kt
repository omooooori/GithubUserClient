import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("jacoco")
            }

            tasks.register("jacocoTestReport", JacocoReport::class) {
                dependsOn("testDebugUnitTest")

                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                val fileFilter = listOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                )

                val debugTree = fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
                    exclude(fileFilter)
                }

                sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
                classDirectories.setFrom(files(debugTree))
                executionData.setFrom(
                    fileTree(project.buildDir) {
                        include("/jacoco/testDebugUnitTest.exec")
                    },
                )
            }
        }
    }
}
