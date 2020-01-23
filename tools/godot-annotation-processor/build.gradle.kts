plugins {
    id("org.jetbrains.kotlin.jvm")
    id("maven")
    id("kotlin-kapt")
}

group = "org.godotengine.kotlin"
version = Dependencies.godotAnnotationProcessorVersion

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":tools:annotations"))
    implementation("de.jensklingenberg:mpapt-runtime:${Dependencies.mpaptVersion}")
    implementation("com.squareup:kotlinpoet:${Dependencies.kotlinPoetVersion}")

    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    compileOnly("com.google.auto.service:auto-service:${Dependencies.googleAutoServiceVersion}")
    kapt("com.google.auto.service:auto-service:${Dependencies.googleAutoServiceVersion}")
}

kapt {
    includeCompileClasspath = true
}

tasks.build {
    finalizedBy(tasks.install)
}