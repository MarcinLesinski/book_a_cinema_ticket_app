package io.stud.forest.book_a_cinema_ticket_app.infrastructure

import org.hibernate.dialect.H2Dialect
import java.sql.Types

class CustomH2Dialect: H2Dialect() {
    init{
        registerColumnType(Types.BINARY, "varbinary")
    }
}