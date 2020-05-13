package no.nav.tpregisteret.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_TSS_TP")
class TpOrdning: AbstractPersistentDomainObject {
    @Column(name = "TSS_ID")
    @Id
    lateinit var id: String

    @Column(name = "TP_ID")
    lateinit var tpNr: String

    @Column(name = "ORGNR")
    lateinit var orgNr: String

    @Column(name = "NAVN")
    lateinit var navn: String
}