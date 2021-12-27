plugins {
    id("java")
    id("groovy")
    id("org.jetbrains.kotlin.jvm").version("1.6.0")
}

group = "org.cufy"
version = "3.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:22.0.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("org.codehaus.groovy:groovy-all:3.0.8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    testImplementation("junit:junit:4.13.2")
}
