package io.stud.forest.book_a_cinema_ticket_app.domain.entities



import io.stud.forest.jpa.FixedIdEntity
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "rooms")
class Room(
        val name: String,
) : FixedIdEntity() {


    //region persist model, use composition or embeddable maybe?
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = [CascadeType.ALL])
    private val _seats: MutableSet<Seat> = mutableSetOf()
    //endregion


    fun addSeat(seat: Seat) {
        seat.room = this
        _seats.add(seat)

    }

    fun addSeats(seats: List<Seat>) {
        seats.forEach(this::addSeat)
    }

    val seats: Set<Seat>
        get() = _seats //TODO: deep copy needed for immutability

    @Transient
    private var _disabilityFriendly: Boolean = false
    @Transient
    val disabilityFriendly = { _disabilityFriendly }

    @PostUpdate
    fun calcDisabilityFriendly() {
        _disabilityFriendly = _seats.any { it.disabilityFriendly }
    }
}