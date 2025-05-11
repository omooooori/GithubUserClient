pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GithubUserClient"
include(":app")
include(":api")
includeBuild("build-logic")
include(":data")
include(":design")
include(":domain")
include(":feature-userdetail")
include(":feature-userlist")
include(":model")
