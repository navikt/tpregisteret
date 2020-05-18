package no.nav.tpregisteret.domain.dto

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Person

data class PersonDto(
        val id: Long,
        val fnr: String,
        val forhold: List<Long>
) {
    constructor(person: Person) : this(
            person.id,
            person.fnr,
            person.forhold.map(Forhold::id)
    )
}