package no.nav.tpregisteret.domain

import org.hibernate.annotations.Where
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_FORHOLD")
data class Forhold(
        @Column(name = "FORHOLD_ID")
        @Id
        val id: Long,
        @ManyToOne
        @JoinColumn(name = "PERSON_ID")
        val person: Person,
        @ManyToOne
        @JoinColumn(name = "TSS_EKSTERN_ID_FK")
        val tpOrdning: TpOrdning,
        @OneToMany(mappedBy = "forhold")
        @Where(clause = "ER_GYLDIG='1'")
        val ytelser: List<Ytelse>,
        @Column(name = "DATO_BRUK_FOM")
        val datoFom: LocalDate,
        @Column(name = "DATO_BRUK_TOM")
        val datoTom: LocalDate?
)