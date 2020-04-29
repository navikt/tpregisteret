package no.nav.tpregisteret.tpordning

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TpRepository(private val jdbcTemplate: JdbcTemplate) {
    companion object {
        private val rowMapper = RowMapper { rs: ResultSet, _: Int -> rs.getString(1) }

        private const val TNFO_QUERY = "SELECT DISTINCT TP_ID FROM T_TSS_TP WHERE T_TSS_TP.ORGNR = ?"
        private const val ORGNR_QUERY = "SELECT DISTINCT ORGNR FROM T_TSS_TP WHERE T_TSS_TP.TSS_ID = ?"
        private const val NAME_QUERY = "SELECT NAVN FROM T_TSS_TP WHERE ORGNR = ?"
        private const val TOFP_QUERY = """SELECT DISTINCT TSS_ID, TP_ID, ORGNR, NAVN FROM T_TSS_TP 
                    INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID 
                    INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID 
                    WHERE T_PERSON.FNR_FK = ? AND T_FORHOLD.ER_GYLDIG = 1 AND T_FORHOLD.HAR_UTLAND_PENSJ = 0"""
        private const val YTELSE_BY_ID_QUERY = "SELECT * FROM TJPEN.T_YTELSE WHERE YTELSE_ID = ?"

        private const val ALL_YTELSE_BY_FORHOLD_ID_QUERY = """
            select
                ytelse.YTELSE_ID as id
            from TJPEN.T_FORHOLD_YTELSE_HISTORIKK link
                left join TJPEN.T_YTELSE ytelse on YTELSE_ID = link.YTELSE_ID_FK
            where link.FORHOLD_ID_FK = ?"""

        private const val FORHOLD_BY_FNR_QUERY = """
            select
                forhold.FORHOLD_ID as id
            from TJPEN.T_PERSON person
                     left join T_FORHOLD forhold on person.PERSON_ID = forhold.PERSON_ID
            where person.FNR_FK = ?"""
    }

    fun getForholdAndYtelserByFnr(fnr: String): Forhold = jdbcTemplate.queryForObject(FORHOLD_BY_FNR_QUERY, Forhold::class.java, fnr)
            .apply { ytelseList = getAllYtelseByForholdId(forholdId) }

    fun getAllYtelseByForholdId(forholdId: String) = jdbcTemplate.queryForList(ALL_YTELSE_BY_FORHOLD_ID_QUERY, Ytelse::class.java, forholdId)
    fun getYtelseByID(ytelseId: String) = jdbcTemplate.query(YTELSE_BY_ID_QUERY, BeanPropertyRowMapper(Ytelse::class.java), ytelseId)

    fun getTpOrdningerForPerson(fnr: String): List<TpOrdning> = jdbcTemplate.query(TOFP_QUERY, BeanPropertyRowMapper(TpOrdning::class.java), fnr)
    fun getTpNrsForOrganisation(orgnr: String): List<String> = jdbcTemplate.query(TNFO_QUERY, rowMapper, orgnr)
    fun getOrganisationName(orgnr: String): List<String> = jdbcTemplate.query(NAME_QUERY, rowMapper, orgnr)
    fun getOrgNrForOrganisation(tssid: String): List<String> = jdbcTemplate.query(ORGNR_QUERY, rowMapper, tssid)
}