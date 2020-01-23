plugins {
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    id("maven")
}

group = "org.godotengine.kotlin"
version = Dependencies.kotlinCompilerPluginVersion

dependencies {
    implementation(project(":tools:godot-annotation-processor"))
    implementation("de.jensklingenberg:mpapt-runtime:${Dependencies.mpaptVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    compileOnly("com.google.auto.service:auto-service:${Dependencies.googleAutoServiceVersion}")
    kapt("com.google.auto.service:auto-service:${Dependencies.googleAutoServiceVersion}")
}

tasks.build {
    dependsOn(":tools:godot-annotation-processor:install")
    finalizedBy(tasks.install)
}

kapt {
    includeCompileClasspath = true
}