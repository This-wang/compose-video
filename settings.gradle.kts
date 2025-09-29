pluginManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven") // 腾讯云镜像
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/This-wang/compose-video")
            credentials {
                username = providers.gradleProperty("gpr.user").get()
                password = providers.gradleProperty("gpr.key").get()
            }
        }
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven") // 腾讯云镜像
        google()
        mavenCentral()
    }
}
rootProject.name = "compose-video"
include(":sample")
include(":compose-video")
include(":compose-video-baselineprof")
