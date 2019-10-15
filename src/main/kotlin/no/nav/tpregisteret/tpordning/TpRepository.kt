package no.nav.tpregisteret.tpordning

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TpRepository(private val jdbcTemplate : JdbcTemplate) {
    private val rowMapper = RowMapper {rs : ResultSet, _: Int -> rs.getString(1)}

    private val tofpQuery =
            "SELECT DISTINCT TSS_ID, TP_ID, ORGNR, NAVN " +
            "FROM T_TSS_TP " +
            "INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID " +
            "INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID " +
            "WHERE T_PERSON.FNR_FK = ? " +
            "AND T_FORHOLD.ER_GYLDIG = 1 " +
            "AND T_FORHOLD.HAR_UTLAND_PENSJ = 0"

    private val tnfoQuery =
            "SELECT DISTINCT TP_ID " +
            "FROM T_TSS_TP " +
            "WHERE T_TSS_TP.ORGNR = ? "

    fun  getTpOrdningerForPerson(fnr : String) : List<TpOrdning>
            = jdbcTemplate.query(tofpQuery, BeanPropertyRowMapper(TpOrdning::class.java), fnr)

    fun getTpNrsForOrganisation(orgnr : String) : List<String>
            = jdbcTemplate.query(tnfoQuery, rowMapper, orgnr)
}