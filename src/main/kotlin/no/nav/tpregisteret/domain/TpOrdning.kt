package no.nav.tpregisteret.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_TSS_TP")
data class TpOrdning(
        @Column(name = "TSS_ID")
        @Id
        val tssId: String,
        @Column(name = "TP_ID")
        val tpId: String,
        @Column(name = "ORGNR")
        val orgNr: String,
        @Column(name = "NAVN")
        val navn: String
)