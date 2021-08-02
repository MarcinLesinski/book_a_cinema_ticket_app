import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20"
//	id ("org.jetbrains.kotlin.plugin.allopen") version "1.5.20"
}

group = "io.stud.forest"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.5.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	/** N + 1 problem detector */
    implementation("com.yannbriancon:spring-hibernate-query-utils:2.0.0")
	/** kotlin utils */
    implementation("io.stud.forest:libbox:0.0.10")
    runtimeOnly("com.h2database:h2:1.4.200")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.2")
    testImplementation("org.mockito:mockito-inline:2.13.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//allOpen {
//	annotations(
//			"javax.persistence.Entity"
//	)
//}

noArg {
    annotations(
			"javax.persistence.Entity"
	)
}
