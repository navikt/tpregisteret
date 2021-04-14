package no.nav.tpregisteret.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_TSS_TP")
class TpOrdning(
    @Suppress("unused") @Column(name = "TSS_ID")
    @Id
    val tssId: String? = null,

    @Column(name = "TP_ID")
    val tpId: String,

    @Column(name = "ORGNR")
    val orgNr: String,

    @Column(name = "NAVN")
    val navn: String
) {
    override fun equals(other: Any?) = other is TpOrdning && other.tpId == tpId
    override fun hashCode() = tpId.hashCode()
}