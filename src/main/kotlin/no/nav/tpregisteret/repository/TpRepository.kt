package no.nav.tpregisteret.repository

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
        private const val YTELSE_BY_ID_QUERY = "SELECT * FROM T_YTELSE WHERE YTELSE_ID = ?"



        private const val TestGetPersonFNR2 = """ 
            select distinct
               person.FNR_FK as fnr
            from T_PERSON person where person.FNR_FK = ?
        """

        private const val testGetTssId2 = """ 
            select distinct
               tp.TP_ID
            from T_PERSON person
                inner join T_TSS_TP tp on tp.TP_ID = ?
            where person.FNR_FK = ?
        """ //TODO we need to check wich tom and fom dato is correct in the database



        private const val GET_FORHOLD_BY_TPID_AND_FNR = """ 
            select distinct
               forhold.FORHOLD_ID as forholdId,
               person.FNR_FK as fnr,
               forhold.DATO_BRUK_TOM as tom, 
               forhold.DATO_BRUK_FOM as fom
            from T_PERSON person
                inner join T_TSS_TP tp on tp.TP_ID = ?
                inner join T_FORHOLD forhold on forhold.TSS_EKSTERN_ID_FK = tp.TSS_ID and forhold.ER_GYLDIG = '1'
            where person.FNR_FK = ?
        """ //TODO we need to check wich tom and fom dato is correct in the database



        private const val GET_ALL_YTELSE_BY_TP_ID_AND_FNR = """
            select distinct
                ytelse.YTELSE_ID,
                ytelse.DATO_BRUK_FOM,
                ytelse.DATO_BRUK_TOM,
                ytelse.K_YTELSE_T
            from T_PERSON person
                inner join T_TSS_TP tp on tp.TP_ID = ?
                inner join T_FORHOLD forhold on forhold.TSS_EKSTERN_ID_FK = tp.TSS_ID and forhold.ER_GYLDIG = '1'
                inner join T_FORHOLD_YTELSE_HISTORIKK link on link.FORHOLD_ID_FK = forhold.FORHOLD_ID
                inner join T_YTELSE ytelse on YTELSE_ID = link.YTELSE_ID_FK
            where person.FNR_FK = ?
        """
    }

    fun testGetPersonFNR(fnr: String): String = jdbcTemplate.queryForObject(TestGetPersonFNR2, String::class.java, fnr)
    fun testGetTssId(tpId: String, fnr: String): String = jdbcTemplate.queryForObject(testGetTssId2, String::class.java, tpId, fnr)


    fun getForholdListTest(tpId: String, fnr: String): List<Forhold> = jdbcTemplate.query(GET_FORHOLD_BY_TPID_AND_FNR, BeanPropertyRowMapper(Forhold::class.java), tpId, fnr)

    fun getForholdByFnrAndTpNr(tpId: String, fnr: String): Forhold = jdbcTemplate.queryForObject(GET_FORHOLD_BY_TPID_AND_FNR, Forhold::class.java, tpId, fnr)

    fun getAllYtelseByForholdIdAndFnr(tpId: String, fnr: String) = jdbcTemplate.queryForList(GET_ALL_YTELSE_BY_TP_ID_AND_FNR, Ytelse::class.java, tpId, fnr)

    fun getYtelseByID(ytelseId: String) = jdbcTemplate.query(YTELSE_BY_ID_QUERY, BeanPropertyRowMapper(Ytelse::class.java), ytelseId)

    fun getTpOrdningerForPerson(fnr: String): List<TpOrdning> = jdbcTemplate.query(TOFP_QUERY, BeanPropertyRowMapper(TpOrdning::class.java), fnr)
    fun getTpNrsForOrganisation(orgnr: String): List<String> = jdbcTemplate.query(TNFO_QUERY, rowMapper, orgnr)
    fun getOrganisationName(orgnr: String): List<String> = jdbcTemplate.query(NAME_QUERY, rowMapper, orgnr)
    fun getOrgNrForOrganisation(tssid: String): List<String> = jdbcTemplate.query(ORGNR_QUERY, rowMapper, tssid)
}