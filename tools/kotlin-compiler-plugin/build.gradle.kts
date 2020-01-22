plugins {
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    id("maven")
}

group = "org.godotengine.kotlin"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":tools:godot-annotation-processor"))
    implementation("de.jensklingenberg:mpapt-runtime:0.8.4-SNAPSHOT") //TODO: bump
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")
}

tasks.build {
    dependsOn(":tools:godot-annotation-processor:install")
    finalizedBy(tasks.install)
}

kapt {
    includeCompileClasspath = true
}