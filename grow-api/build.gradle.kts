dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("org.springframework:spring-tx")

    implementation ("io.springfox:springfox-swagger2:3.0.0")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":grow-common"))
    implementation(project(":grow-core"))
    implementation(project(":grow-infra"))

    testImplementation ("org.springframework.boot:spring-boot-starter-test")

}

tasks.bootJar {
    enabled = true
}