dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("org.springframework:spring-tx")

    implementation(project(":infinity-common"))
    implementation(project(":infinity-core"))
    implementation(project(":infinity-infra"))
}

tasks.bootJar {
    enabled = true
}