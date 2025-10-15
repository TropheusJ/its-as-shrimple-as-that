plugins {
	alias(libs.plugins.loom)
	alias(libs.plugins.maven)
}

base.archivesName = "its_as_shrimple_as_that"
group = "io.github.tropheusj"

val buildNum = providers.environmentVariable("GITHUB_RUN_NUMBER")
    .filter(String::isNotEmpty)
    .getOrElse("99999")

version = "2.0.$buildNum+mc${libs.versions.minecraft.get()}"

repositories {
	exclusiveContent {
        forRepositories(maven("https://api.modrinth.com/maven")).filter {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
	minecraft(libs.minecraft)
	mappings(loom.officialMojangMappings())

	modImplementation(libs.bundles.fabric)
    modLocalRuntime(libs.bundles.dev)
}

tasks.processResources {
	val properties: Map<String, Any> = mapOf(
		"version" to version,
        "minecraft_version" to libs.versions.minecraft.get(),
		"loader_version" to libs.versions.loader.get(),
		"fapi_version" to libs.versions.fapi.get()
	)

	inputs.properties(properties)

	filesMatching("fabric.mod.json") {
		expand(properties)
	}
}

loom.runs.configureEach {
    property("mixin.debug.export", "true")
}

java {
	withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	repositories {
        listOf("Releases", "Snapshots").forEach {
            maven("https://mvn.devos.one/${it.lowercase()}") {
                name = "devOs$it"
                credentials(PasswordCredentials::class)
            }
        }
	}
}
