plugins {
    id("java")
    id("me.champeau.jmh") version "0.6.8"
}

group = "dev.slys.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.mockito:mockito-core:4.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.11.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    runtimeOnly("com.google.guava:guava:31.1-jre")
    implementation("com.google.guava:guava:31.1-jre")

    jmh("org.openjdk.jmh:jmh-core:1.34")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.34")
    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.34")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
