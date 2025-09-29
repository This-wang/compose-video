// publish.gradle.kts
import java.io.File
import java.util.Properties
plugins.withId("com.android.library") {
    apply(plugin = "maven-publish")

    // 读取 version.properties 文件的函数
    fun readVersionProperties(): Properties {
        val versionPropsFile = rootProject.file("gradle/version.properties")
        return Properties().apply {
            if (versionPropsFile.exists()) {
                versionPropsFile.reader().use { load(it) }
            } else {
                throw GradleException("version.properties file not found at: ${versionPropsFile.absolutePath}")
            }
        }
    }

// 读取版本信息
    val versionProps = readVersionProperties()
    val publishingGroupId = versionProps.getProperty("publishingGroupId")
    val publishingArtifactId = versionProps.getProperty("publishingArtifactId")
    val publishingVersion = versionProps.getProperty("publishingVersion")

    // 验证必要的属性
    fun validatePublishingProperties() {
        val requiredProperties = listOf("publishingGroupId", "publishingArtifactId", "publishingVersion")
        val missingProperties = requiredProperties.filter { versionProps.getProperty(it).isNullOrEmpty() }

        if (missingProperties.isNotEmpty()) {
            throw GradleException("Missing required properties in version.properties: ${missingProperties.joinToString()}")
        }

        println("=== Publishing Configuration ===")
        println("Group: $publishingGroupId")
        println("Artifact: $publishingArtifactId")
        println("Version: $publishingVersion")
        println("================================")
    }

    // 执行验证
    validatePublishingProperties()

    // 配置发布任务
    afterEvaluate {
        extensions.configure< PublishingExtension> {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/This-wang/compose-video")
                    credentials {
                        username = System.getenv("GITHUB_PACKAGE_USER") ?:  project.findProperty("gpr.user") as String
                        password = System.getenv("GITHUB_PACKAGE_TOKEN") ?: project.findProperty("gpr.key") as String
                    }
                }
            }

            publications {
                register<MavenPublication>("gpr") {
                    groupId = publishingGroupId
                    artifactId =publishingArtifactId
                    version = publishingVersion

                    // 对于 Android 库，使用 release 组件
                    if (project.plugins.hasPlugin("com.android.library")) {
                        from(components["release"])
                    }
                }
            }
        }
    }

}
