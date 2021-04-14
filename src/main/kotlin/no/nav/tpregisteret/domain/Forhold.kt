package no.nav.tpregisteret.domain

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.Where
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_FORHOLD")
data class Forhold(
    @Column(name = "FORHOLD_ID")
    @Id
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    val person: Person,

    @ManyToOne
    @JoinColumn(name = "TSS_EKSTERN_ID_FK")
    val tpOrdning: TpOrdning,

    @OneToMany(mappedBy = "forhold")
    @Where(clause = "ER_GYLDIG='1'")
    @JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator::class)
    val ytelser: List<Ytelse>,

    @Column(name = "DATO_BRUK_FOM")
    val datoFom: LocalDate,

    @Column(name = "DATO_BRUK_TOM")
    val datoTom: LocalDate? = null
) {
    override fun equals(other: Any?) = other is Forhold
            && other.person == person
            && other.tpOrdning == tpOrdning
            && other.datoFom == datoFom
            && other.datoTom == datoTom

    override fun hashCode(): Int {
        var result = person.hashCode()
        result = 31 * result + tpOrdning.hashCode()
        result = 31 * result + datoFom.hashCode()
        result = 31 * result + (datoTom?.hashCode() ?: 0)
        return result
    }
}