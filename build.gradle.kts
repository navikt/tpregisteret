import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "no.nav"
version = "1"
description = "tpregisteret"


plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.adarshr.test-logger") version "1.6.0" // pretty log printing
}

repositories {
    mavenCentral()
}


dependencies {
    implementation( kotlin("stdlib"))
    implementation( "org.springframework.boot","spring-boot-starter","2.1.7.RELEASE")
    implementation( "org.springframework.boot","spring-boot-starter-jdbc","2.1.7.RELEASE")
    implementation( "org.springframework.boot","spring-boot-starter-web","2.1.7.RELEASE")
    implementation( "org.springframework.boot","spring-boot-starter-actuator","2.1.7.RELEASE")
    implementation( "org.springframework.boot","spring-boot-starter-oauth2-resource-server","2.1.7.RELEASE")
    implementation( "org.springframework.security.oauth","spring-security-oauth2","2.3.6.RELEASE")
    implementation( "com.oracle.ojdbc","ojdbc8","19.3.0.0")
    implementation( "io.micrometer","micrometer-registry-prometheus","1.1.5")
    implementation( "ch.qos.logback","logback-classic","1.2.3")
    implementation( "net.logstash.logback","logstash-logback-encoder","6.2")
    implementation( "javax.xml.bind","jaxb-api","2.3.0")
    implementation( "com.sun.xml.bind","jaxb-impl","2.3.0")
    implementation( "org.glassfish.jaxb","jaxb-runtime","2.3.0")
    implementation( "javax.activation","activation","1.1.1")
    testImplementation( "org.jetbrains.kotlin","kotlin-test-junit5","1.3.50")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation( "com.h2database","h2","1.4.199")
    testImplementation( "com.github.tomakehurst","wiremock","2.23.2")
}


tasks{
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "13"
    }
    withType<Test> {
        useJUnitPlatform()
    }
}