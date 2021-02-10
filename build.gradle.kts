import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "no.nav"
version = "1"
description = "tpregisteret"

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.noarg") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/navikt/maskinporten-validation")
        credentials {
            username = "token"
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.11.2")
    implementation("com.oracle.database.jdbc", "ojdbc10", "19.7.0.0")
    implementation("io.micrometer", "micrometer-registry-prometheus", "1.5.5")
    implementation("net.logstash.logback", "logstash-logback-encoder", "6.4")
    implementation("no.nav.pensjonsamhandling", "maskinporten-validation-spring", "0.0.6")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-jdbc")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    testImplementation(kotlin("test-junit5"))
    testImplementation("no.nav.pensjonsamhandling", "maskinporten-validation-spring-test", "0.0.6")
    testImplementation("org.springframework.boot", "spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testRuntimeOnly("com.h2database", "h2", "1.4.200")
}

noArg {
    annotation("javax.persistence.Entity")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "15"
    }
    test {
        useJUnitPlatform()
    }
}
