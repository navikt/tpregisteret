package no.nav.tpregisteret.domain

import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "T_PERSON")
class Person: AbstractPersistentDomainObject {
    @Column(name = "PERSON_ID")
    @Id
    lateinit var id: Number

    @Column(name = "FNR_FK")
    lateinit var fnr: String

    @OneToMany(mappedBy = "person")
    @Where(clause = "ER_GYLDIG='1'")
    lateinit var forhold: List<Forhold>
}