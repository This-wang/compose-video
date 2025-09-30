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
            url = uri("https://maven.pkg.github.com/LojaHuang/nf-chat-core")
            credentials {
                username = System.getenv("GITHUB_PACKAGE_USER")
                password = System.getenv("GITHUB_PACKAGE_TOKEN")
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
