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
    implementation("com.oracle.ojdbc","ojdbc8","19.3.0.0")
    implementation("io.micrometer","micrometer-registry-prometheus","1.1.5")
    implementation("net.logstash.logback","logstash-logback-encoder","6.2")
    implementation("no.nav.security","token-validation-spring", "1.2.0")
    implementation("org.springframework.boot","spring-boot-starter-web")
    implementation("org.springframework.boot","spring-boot-starter-jdbc")
    implementation("org.springframework.boot","spring-boot-starter-data-jpa")
    implementation("org.springframework.boot","spring-boot-starter-actuator")
    testImplementation(kotlin("test-junit5"))
    testImplementation("no.nav.security","token-validation-test-support", "1.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testRuntimeOnly("com.h2database","h2","1.4.199")
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "13"
    }
    test{
        useJUnitPlatform()
    }
}
