package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_YTELSE")
class Ytelse {

    @Id
    @Column(name = "YTELSE_ID")
    var id: Long = 0

    @ManyToOne
    @JoinTable(name = "T_FORHOLD_YTELSE_HISTORIKK",
            inverseJoinColumns = [JoinColumn(name = "FORHOLD_ID_FK")],
            joinColumns = [JoinColumn(name = "YTELSE_ID_FK")]
    )
    lateinit var forhold: Forhold

    @Column(name = "K_YTELSE_T")
    lateinit var type: String

    @Column(name = "DATO_BRUK_FOM")
    lateinit var datoFom: LocalDate

    @Column(name = "DATO_BRUK_TOM")
    var datoTom: LocalDate? = null

    @Column(name = "DATO_OPPRETTET")
    var datoOpprettet: LocalDate? = null
}