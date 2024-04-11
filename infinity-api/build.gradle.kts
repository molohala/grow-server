dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("org.springframework:spring-tx")

    implementation ("io.springfox:springfox-swagger2:3.0.0")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":infinity-common"))
    implementation(project(":infinity-core"))
    implementation(project(":infinity-infra"))
}

tasks.bootJar {
    enabled = true
}