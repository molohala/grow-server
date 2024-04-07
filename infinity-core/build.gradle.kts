dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")

    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly ("com.mysql:mysql-connector-j")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation(project(":infinity-common"))
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}