package no.nav.tpregisteret.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_TSS_TP")
class TpOrdning {
    @Column(name = "TSS_ID")
    @Id
    lateinit var tssId: String

    @Column(name = "TP_ID")
    lateinit var tpId: String

    @Column(name = "ORGNR")
    lateinit var orgNr: String

    @Column(name = "NAVN")
    lateinit var navn: String
}