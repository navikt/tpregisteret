package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.exceptions.TpOrdningIkkeFunnet
import no.nav.tpregisteret.exceptions.YtelseIkkeFunnet
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TpRepository(private val jdbcTemplate: JdbcTemplate) {
    companion object {
        private val rowMapper = RowMapper { rs: ResultSet, _: Int -> rs.getString(1) }
        private val forholdMapper = RowMapper { rs, _ -> Forhold(
                rs.getString("FORHOLD_ID"),
                rs.getString("FNR"),
                rs.getDate("DATO_BRUK_FOM").toLocalDate(),
                rs.getDate("DATO_BRUK_TOM")?.toLocalDate()
        )}

        private val ytelseMapper = RowMapper { rs, _ -> Ytelse(
                rs.getString("YTELSE_ID"),
                rs.getString("FNR"),
                rs.getDate("DATO_BRUK_FOM").toLocalDate(),
                rs.getDate("DATO_BRUK_TOM")?.toLocalDate()
        )}

        private const val TNFO_QUERY = "SELECT DISTINCT TP_ID FROM T_TSS_TP WHERE T_TSS_TP.ORGNR = ?"
        private const val ORGNR_QUERY = "SELECT DISTINCT ORGNR FROM T_TSS_TP WHERE T_TSS_TP.TSS_ID = ?"
        private const val NAME_QUERY = "SELECT NAVN FROM T_TSS_TP WHERE ORGNR = ?"
        private const val TOFP_QUERY = """SELECT DISTINCT TSS_ID, TP_ID, ORGNR, NAVN FROM T_TSS_TP 
                    INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID 
                    INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID 
                    WHERE T_PERSON.FNR_FK = ? AND T_FORHOLD.ER_GYLDIG = 1 AND T_FORHOLD.HAR_UTLAND_PENSJ = 0"""
        private const val YTELSE_BY_ID_QUERY = "SELECT * FROM T_YTELSE WHERE YTELSE_ID = ?"



        private const val GET_TSS_ID_BY_TP_ID = """select TSS_ID from T_TSS_TP where TP_ID = ?"""

        private const val GET_PERSON_ID_BY_FNR = """select PERSON_ID from T_PERSON where FNR_FK = ?"""

        private const val GET_GYLDIG_FORHOLD_ID_BY_PERSON_ID_AND_TSS_ID =
                """select FORHOLD_ID from T_FORHOLD where PERSON_ID = ? and TSS_EKSTERN_ID_FK = ?"""

        private const val GET_GYLDIG_FORHOLD_BY_PERSON_ID_AND_TSS_ID = """
                select 
                  FORHOLD_ID,
                   ? as FNR,
                   DATO_BRUK_TOM,
                   DATO_BRUK_FOM
               from T_FORHOLD where PERSON_ID = ? and TSS_EKSTERN_ID_FK = ?
        """



        private const val GET_ALL_YTELSE_BY_TP_ID = """
            select distinct
                ytelse.YTELSE_ID,
                ? as FNR,
                ytelse.DATO_BRUK_FOM,
                ytelse.DATO_BRUK_TOM
            from T_YTELSE ytelse
                inner join T_FORHOLD_YTELSE_HISTORIKK link on link.FORHOLD_ID_FK = ? and ytelse.YTELSE_ID = link.YTELSE_ID_FK
        """
    }

    private fun personIdByFnr(fnr: String) =
            jdbcTemplate.queryForObject(GET_PERSON_ID_BY_FNR, String::class.java, fnr) ?: throw PersonIkkeFunnet()

    private fun getTssIdByTpId(tpnr: String) =
            jdbcTemplate.queryForObject(GET_TSS_ID_BY_TP_ID, String::class.java, tpnr) ?: throw TpOrdningIkkeFunnet()

    private fun getGyldigForholdIdByPersonIdAndTssId(fnr: String, tpnr: String) =
            jdbcTemplate.queryForObject(
                    GET_GYLDIG_FORHOLD_ID_BY_PERSON_ID_AND_TSS_ID,
                    String::class.java,
                    personIdByFnr(fnr),
                    getTssIdByTpId(tpnr)
            ) ?: throw ForholdIkkeFunnet()

    fun getForholdByFnrAndTpNr(fnr: String, tpnr: String) =
            jdbcTemplate.queryForObject(
                    GET_GYLDIG_FORHOLD_BY_PERSON_ID_AND_TSS_ID,
                    forholdMapper,
                    fnr,
                    personIdByFnr(fnr),
                    getTssIdByTpId(tpnr)
            ) ?: throw ForholdIkkeFunnet()

    fun getAllYtelseByForholdIdAndFnr(fnr: String, tpnr: String) =
            jdbcTemplate.query(
                    GET_ALL_YTELSE_BY_TP_ID,
                    ytelseMapper,
                    fnr,
                    getGyldigForholdIdByPersonIdAndTssId(fnr, tpnr)
            ).ifEmpty { throw YtelseIkkeFunnet() }

    fun getYtelseByID(ytelseId: String) = jdbcTemplate.query(YTELSE_BY_ID_QUERY, BeanPropertyRowMapper(Ytelse::class.java), ytelseId)

    fun getTpOrdningerForPerson(fnr: String): List<TpOrdning> = jdbcTemplate.query(TOFP_QUERY, BeanPropertyRowMapper(TpOrdning::class.java), fnr)
    fun getTpNrsForOrganisation(orgnr: String): List<String> = jdbcTemplate.query(TNFO_QUERY, rowMapper, orgnr)
    fun getOrganisationName(orgnr: String): List<String> = jdbcTemplate.query(NAME_QUERY, rowMapper, orgnr)
    fun getOrgNrForOrganisation(tssid: String): List<String> = jdbcTemplate.query(ORGNR_QUERY, rowMapper, tssid)
}