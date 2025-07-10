plugins {
    id("edu.sc.seis.launch4j") version "2.5.0"
    application
    java
}

val mainClassPath = "View.Personalmenu.MenuLogin"
val applicationVersion = "1.0.0"

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

    // commons-logging-1.1.3.jar
    implementation("commons-logging:commons-logging:1.1.3")
    // hsqldb.jar
    implementation("org.hsqldb:hsqldb:2.7.1") // o una versione compatibile con UCanAccess
    // jackcess-2.1.11.jar
    implementation("com.healthmarketscience.jackcess:jackcess:2.1.11")
    // ucanaccess-4.0.4.jar
    implementation("net.sf.ucanaccess:ucanaccess:4.0.4")
    // commons-lang-2.6.jar
    implementation("commons-lang:commons-lang:2.6")
    // JTattoo-1.6.11.jar
    implementation("com.jtattoo:JTattoo:1.6.11")
    // javax.xml.bind
    implementation("javax.xml.bind:jaxb-api:2.3.1")
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
