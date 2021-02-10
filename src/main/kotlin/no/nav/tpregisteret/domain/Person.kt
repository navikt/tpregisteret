package no.nav.tpregisteret.domain

import org.hibernate.annotations.Where
import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "T_PERSON")
data class Person(
        @Column(name = "PERSON_ID")
        @Id
        val id: Long,
        @Column(name = "FNR_FK")
        val fnr: String,
        @OneToMany(mappedBy = "person", fetch = EAGER)
        @Where(clause = "ER_GYLDIG='1' AND HAR_UTLAND_PENSJ='0'")
        val forhold: List<Forhold>
)