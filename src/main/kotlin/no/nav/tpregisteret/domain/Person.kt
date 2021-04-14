package no.nav.tpregisteret.domain

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.Where
import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "T_PERSON")
class Person(
    @Column(name = "PERSON_ID")
    @Id
    val id: Long? = null,

    @Column(name = "FNR_FK")
    val fnr: String,

    @OneToMany(mappedBy = "person", fetch = EAGER)
    @Where(clause = "ER_GYLDIG='1' AND HAR_UTLAND_PENSJ='0'")
    @JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator::class)
    val forhold: List<Forhold>
) {
    override fun equals(other: Any?) = other is Person && other.fnr == fnr

    override fun hashCode() = fnr.hashCode()
}