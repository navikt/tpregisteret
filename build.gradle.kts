import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "no.nav"
version = "1"
description = "tpregisteret"

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("org.springframework.boot") version "2.2.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot","spring-boot-starter-web")
    implementation("org.springframework.boot","spring-boot-starter-jdbc")
    implementation("org.springframework.boot","spring-boot-starter-data-jpa")
    implementation("org.springframework.boot","spring-boot-starter-actuator")
    implementation("org.springframework.boot","spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.security.oauth","spring-security-oauth2","2.3.6.RELEASE")
    implementation("com.oracle.ojdbc","ojdbc8","19.3.0.0")
    implementation("io.micrometer","micrometer-registry-prometheus","1.1.5")
    implementation("net.logstash.logback","logstash-logback-encoder","6.2")
    implementation("com.sun.xml.bind","jaxb-impl","2.3.0")
    implementation("org.glassfish.jaxb","jaxb-runtime","2.3.0")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("com.h2database","h2","1.4.199")
    testImplementation("com.github.tomakehurst","wiremock","2.23.2")
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "13"
    }
    test{
        useJUnitPlatform()
    }
}