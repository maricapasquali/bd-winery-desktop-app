plugins {
    id("edu.sc.seis.launch4j") version "2.5.0"
    application
    java
}

val mainClassPath = "com.winery.desktop.Main"
val applicationVersion = "1.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClass.set(mainClassPath)
    version = applicationVersion
}

repositories {
    mavenCentral()
}

dependencies {
    // common-lang3.jar
    implementation(files("lib/common-lang3.jar"))
    // jcalendar-1.4.jar
    implementation(files("lib/jcalendar-1.4.jar"))
    // JTattoo-1.6.11.jar
    implementation("com.jtattoo:JTattoo:1.6.11")
    // javax.xml.bind
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // DATABASE
    // Microsoft Access: ucanaccess-5.0.1.jar
    implementation("net.sf.ucanaccess:ucanaccess:5.0.1")
    // postgresSQL
    implementation("org.postgresql:postgresql:42.7.7")
}

fun loadEnv(): Map<String, String> {
    val envFile = rootProject.file(".env")
    val env = mutableMapOf<String, String>()
    if (envFile.exists()) {
        envFile.forEachLine { line ->
            if (line.isNotBlank() && !line.trim().startsWith("#")) {
                val (key, value) = line.split("=", limit = 2)
                env[key.trim()] = value.trim()
            }
        }
    } else {
        throw GradleException("No .env file found")
    }
    return env
}

val generateConfig by tasks.registering {
    val envVars = loadEnv()
    if (envVars.isEmpty()) throw GradleException("No environment variables specified in .env file")

    val inputFile = sourceSets["main"].resources.files.find { it.name == "config.properties" }?.absoluteFile
    if (inputFile == null) throw GradleException("config.properties file does not exist.")

    val outputFile = layout.buildDirectory.file("resources/main/config.properties").get().asFile

    outputs.file(outputFile)
    inputs.file(inputFile)
    outputs.upToDateWhen { false } // always force the generation

    doLast {
        var content = inputFile.readText()

        content = Regex("""\$\{([A-Z0-9_]+)}""").replace(content) { match ->
            val key = match.groupValues[1]
            envVars[key] ?: throw GradleException("Variable ${key} not found in .env")
        }

        outputFile.parentFile.mkdirs()
        outputFile.writeText(content)
        println("config.properties is generated from .env file.")
    }
}

tasks.named<ProcessResources>("processResources") {
    exclude("config.properties")

    dependsOn(generateConfig)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = mainClassPath
    }

    // Include all dependencies (fat jar)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

launch4j {
    mainClassName = mainClassPath
    val appName = tasks.jar.get().archiveFile.get().asFile.name.removeSuffix(".jar")
    jar = "$appName.jar"
    outfile = "$appName.exe"
    icon = layout.buildDirectory.file("resources/main/ico/grape-icon.ico").get().asFile.absolutePath
    headerType = "gui"
    jreMinVersion = "1.8.0"
    version = applicationVersion
}

tasks.register<JavaExec>("accessToPostgres") {
    group = "data migration"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("script.DataMigratorAccessToPostgres")
}
