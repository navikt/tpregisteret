package no.nav.tpregisteret.domain

import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "T_PERSON")
class Person {

    @Column(name = "PERSON_ID")
    @Id
    var id: Long = 0

    @Column(name = "FNR_FK")
    lateinit var fnr: String

    @OneToMany(mappedBy = "person")
    @Where(clause = "ER_GYLDIG='1' AND HAR_UTLAND_PENSJ='0'")
    lateinit var forhold: List<Forhold>
}