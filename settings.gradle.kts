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
include(":data")
include(":model")
include(":domain")
include(":design")
include(":feature-userlist")
include(":feature-userdetail")
