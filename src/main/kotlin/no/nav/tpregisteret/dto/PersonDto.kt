package no.nav.tpregisteret.dto

import no.nav.tpregisteret.domain.Person

class PersonDto {
    var id: Long = 0
    var fnr: String
    var forhold: List<ForholdDto>

    constructor(person: Person) {
        this.id = person.id
        this.fnr = person.fnr
        this.forhold = person.forhold.map{ ForholdDto(it) }
    }
}