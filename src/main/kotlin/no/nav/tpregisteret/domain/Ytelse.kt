package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_YTELSE")
data class Ytelse(
        @Id
        @Column(name = "YTELSE_ID")
        val id: Long,
        @ManyToOne
        @JoinTable(name = "T_FORHOLD_YTELSE_HISTORIKK",
                inverseJoinColumns = [JoinColumn(name = "FORHOLD_ID_FK")],
                joinColumns = [JoinColumn(name = "YTELSE_ID_FK")]
        )
        val forhold: Forhold,
        @Column(name = "K_YTELSE_T")
        val type: String,
        @Column(name = "DATO_YTEL_IVER_FOM")
        val datoFom: LocalDate?,
        @Column(name = "DATO_YTEL_IVER_TOM")
        val datoTom: LocalDate?,
        @Column(name = "DATO_INNM_YTEL_FOM")
        val medlemskapDatoFom: LocalDate?
)