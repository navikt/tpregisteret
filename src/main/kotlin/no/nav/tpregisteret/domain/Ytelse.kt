package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "T_YTELSE")
data class Ytelse(
    @Id
    @Column(name = "YTELSE_ID")
    val id: Long
) {
    @ManyToOne
    @JoinTable(
        name = "T_FORHOLD_YTELSE_HISTORIKK",
        inverseJoinColumns = [JoinColumn(name = "FORHOLD_ID_FK")],
        joinColumns = [JoinColumn(name = "YTELSE_ID_FK")]
    )
    lateinit var forhold: Forhold

    @Column(name = "K_YTELSE_T")
    lateinit var type: String

    @Column(name = "DATO_YTEL_IVER_FOM")
    var datoFom: LocalDate? = null

    @Column(name = "DATO_YTEL_IVER_TOM")
    var datoTom: LocalDate? = null

    @Column(name = "DATO_INNM_YTEL_FOM")
    var medlemskapDatoFom: LocalDate? = null

    val fnr: String
        get() = forhold.person.fnr
}