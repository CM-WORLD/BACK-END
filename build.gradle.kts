plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.blog"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation ("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE") //새로 추가
    implementation ("com.amazonaws:aws-java-sdk-s3:1.11.901") //front_tubeat gradle
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.json:json:20211205")

    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.google.code.gson:gson:2.6.2")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // 기본적인 Project Reactor 의존성
    implementation ("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("io.projectreactor:reactor-core:3.5.4") // 원하는 버전으로 변경

    //twitter
    implementation ("org.springframework.social:spring-social-twitter:1.1.2.RELEASE")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.4")


    //swagger ui
//    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")



    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    compileOnly("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
