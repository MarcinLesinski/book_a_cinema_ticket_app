pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}

sourceControl{
	gitRepository(java.net.URI.create("https://github.com/MarcinLesinski/forest-libbox")){
		producesModule("io.stud.forest:libbox")
	}
}

rootProject.name = "book_a_cinema_ticket_app"
