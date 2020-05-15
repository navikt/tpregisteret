package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_FORHOLD")
class Forhold {
    @Column(name = "FORHOLD_ID")
    @Id
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    lateinit var person: Person

    @ManyToOne
    @JoinColumn(name = "TSS_EKSTERN_ID_FK")
    lateinit var tpOrdning: TpOrdning

    @OneToMany(mappedBy = "forhold")
    lateinit var ytelser: List<Ytelse>

    @Column(name = "DATO_BRUK_FOM")
    lateinit var datoFom: LocalDate

    @Column(name = "DATO_BRUK_TOM")
    var datoTom: LocalDate? = null
}