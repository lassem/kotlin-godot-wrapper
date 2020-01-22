buildscript {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.0.0")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    id("maven")
    id("maven-publish")
}

apply(plugin = "com.github.johnrengelman.shadow")

group = "org.godotengine.kotlin"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":tools:godot-annotation-processor"))
    implementation("de.jensklingenberg:mpapt-runtime:0.8.4-SNAPSHOT") //TODO: bump
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler")
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")
}

val fatJar = task<Jar>("fatJar") {
    manifest {
        attributes["Implementation-Title"] = "Godot Kotlin Native Compiler Plugin Fat Jar"
        attributes["Implementation-Version"] = project.version
        attributes["Main-Class"] = "org.godotengine.kotlin.compilerplugin.NativeComponentRegistrar"
    }
    baseName = "kotlin-compiler-native-plugin"
    version = project.version.toString()

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

val shadowJar by tasks.getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    manifest.inheritFrom(fatJar.manifest)
    baseName = "kotlin-compiler-native-plugin"
    version = project.version.toString()
    classifier = null
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            configure<com.github.jengelman.gradle.plugins.shadow.ShadowExtension> {
                component(this@create)
            }
        }
    }
}

tasks.install {
    dependsOn(shadowJar)
}

tasks.build {
    dependsOn(":tools:godot-annotation-processor:install", shadowJar)
    finalizedBy(tasks.install)
}

kapt {
    includeCompileClasspath = true
}