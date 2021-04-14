package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_YTELSE")
class Ytelse(
    @Id
    @Column(name = "YTELSE_ID")
    val id: Long? = null,

    @ManyToOne
    @JoinTable(
        name = "T_FORHOLD_YTELSE_HISTORIKK",
        inverseJoinColumns = [JoinColumn(name = "FORHOLD_ID_FK")],
        joinColumns = [JoinColumn(name = "YTELSE_ID_FK")]
    )
    val forhold: Forhold,

    @Column(name = "K_YTELSE_T")
    val type: String,

    @Column(name = "DATO_YTEL_IVER_FOM")
    val datoFom: LocalDate? = null,

    @Column(name = "DATO_YTEL_IVER_TOM")
    val datoTom: LocalDate? = null,

    @Column(name = "DATO_INNM_YTEL_FOM")
    val medlemskapDatoFom: LocalDate? = null
) {
    val fnr: String
        get() = forhold.person.fnr

    override fun equals(other: Any?) = other is Ytelse
            && other.forhold == forhold
            && other.type == type
            && other.datoFom == datoFom
            && other.datoTom == datoTom
            && other.medlemskapDatoFom == medlemskapDatoFom

    override fun hashCode(): Int {
        var result = forhold.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (datoFom?.hashCode() ?: 0)
        result = 31 * result + (datoTom?.hashCode() ?: 0)
        result = 31 * result + (medlemskapDatoFom?.hashCode() ?: 0)
        return result
    }
}