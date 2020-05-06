package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Ytelse
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

val rowMapper = RowMapper { rs: ResultSet, _: Int -> rs.getString(1) }
val forholdMapper = RowMapper { rs, _ ->
    Forhold(
            rs.getString("FORHOLD_ID"),
            rs.getString("FNR"),
            rs.getDate("DATO_BRUK_FOM").toLocalDate(),
            rs.getDate("DATO_BRUK_TOM")?.toLocalDate()
    )
}

val ytelseMapper = RowMapper { rs, _ ->
    Ytelse(
            rs.getString("YTELSE_ID"),
            rs.getString("FNR"),
            rs.getDate("DATO_BRUK_FOM").toLocalDate(),
            rs.getDate("DATO_BRUK_TOM")?.toLocalDate()
    )
}