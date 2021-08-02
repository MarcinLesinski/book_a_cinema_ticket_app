package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.jpa.FixedIdEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "movies")
class Movie: FixedIdEntity() {
    var title: String = ""
    var description: String = ""
    @Column(name = "duration_in_seconds")
    var durationInMinutes: Int = 0
    @Column(name = "legal_age")
    var legalAge: Int = 0
    var author: String = ""
}