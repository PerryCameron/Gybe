import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.palantir.git-version") version "3.1.0"  // latest release on Jun 5, 2024
}

group = "com.ecsail"
//version = "1.3.4-SNAPSHOT"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.poi:poi-ooxml-lite:5.2.5")
    implementation("com.itextpdf:itext-core:8.0.4")
    testImplementation("junit:junit:4.13.1")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.register("generateBuildInfoProperties") {
    doLast {
        // Create timestamp
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val buildTimestamp = dateFormat.format(Date())
        val javaVersion = System.getProperty("java.version") ?: "Unknown"
        // Generate a combined properties file
        val propertiesFile = file("src/main/resources/version.properties")
        propertiesFile.parentFile.mkdirs()
        propertiesFile.writeText("""
           version=${project.version}
           build.timestamp=$buildTimestamp
           java.version=$javaVersion
       """.trimIndent())
    }
}

tasks.processResources {
    dependsOn("generateBuildInfoProperties")
}


tasks.named<Test>("test") {
    useJUnitPlatform()
}

springBoot {
    buildInfo()
}