package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.exceptions.TpOrdningIkkeFunnet
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class PersonRepository(private val jdbcTemplate: JdbcTemplate) {
    companion object {
        private const val TOFP_QUERY = """SELECT DISTINCT TSS_ID, TP_ID, ORGNR, NAVN FROM T_TSS_TP 
                    INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID 
                    INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID 
                    WHERE T_PERSON.FNR_FK = ? AND T_FORHOLD.ER_GYLDIG = 1 AND T_FORHOLD.HAR_UTLAND_PENSJ = 0"""

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
               from T_FORHOLD where PERSON_ID = ? and TSS_EKSTERN_ID_FK = ?"""


        private const val GET_ALL_YTELSE_BY_TP_ID = """
            select distinct
                ytelse.YTELSE_ID,
                ? as FNR,
                ytelse.DATO_BRUK_FOM,
                ytelse.DATO_BRUK_TOM
            from T_YTELSE ytelse
                inner join T_FORHOLD_YTELSE_HISTORIKK link on link.FORHOLD_ID_FK = ? and ytelse.YTELSE_ID = link.YTELSE_ID_FK"""
    }

    private inline fun <reified T> queryForNullable(sql: String, vararg args: Any): T? = try {
        jdbcTemplate.queryForObject(sql, T::class.java, *args)
    } catch (_: EmptyResultDataAccessException) {
        null
    }

    private fun <T> queryForNullable(sql: String, rowMapper: RowMapper<T>, vararg args: Any): T? = try {
        jdbcTemplate.queryForObject(sql, rowMapper, *args)
    } catch (_: EmptyResultDataAccessException) {
        null
    }

    private fun personIdByFnr(fnr: String) =
            queryForNullable<String>(GET_PERSON_ID_BY_FNR, fnr) ?: throw PersonIkkeFunnet()

    private fun getTssIdByTpId(tpnr: String) =
            queryForNullable<String>(GET_TSS_ID_BY_TP_ID, tpnr) ?: throw TpOrdningIkkeFunnet()

    private fun getGyldigForholdIdByPersonIdAndTssId(fnr: String, tpnr: String) =
            queryForNullable<String>(
                    GET_GYLDIG_FORHOLD_ID_BY_PERSON_ID_AND_TSS_ID,
                    personIdByFnr(fnr),
                    getTssIdByTpId(tpnr)
            ) ?: throw ForholdIkkeFunnet()

    fun getForholdByFnrAndTpNr(fnr: String, tpnr: String) =
        queryForNullable(
                GET_GYLDIG_FORHOLD_BY_PERSON_ID_AND_TSS_ID,
                forholdMapper,
                fnr,
                personIdByFnr(fnr),
                getTssIdByTpId(tpnr)
        ) ?: throw ForholdIkkeFunnet()

    fun getAllYtelseByForholdIdAndFnr(fnr: String, tpnr: String): List<Ytelse> =
            jdbcTemplate.query(
                    GET_ALL_YTELSE_BY_TP_ID,
                    ytelseMapper,
                    fnr,
                    getGyldigForholdIdByPersonIdAndTssId(fnr, tpnr)
            )

    fun getTpOrdningerForPerson(fnr: String): List<TpOrdning> = jdbcTemplate.query(TOFP_QUERY, BeanPropertyRowMapper(TpOrdning::class.java), fnr)
}