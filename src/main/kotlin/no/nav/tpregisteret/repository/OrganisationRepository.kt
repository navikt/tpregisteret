//package no.nav.tpregisteret.repository
//
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.stereotype.Repository
//
//@Repository
//class OrganisationRepository(private val jdbcTemplate: JdbcTemplate) {
//    companion object {
//        private const val TNFO_QUERY = "SELECT DISTINCT TP_ID FROM T_TSS_TP WHERE T_TSS_TP.ORGNR = ?"
//        private const val ORGNR_QUERY = "SELECT DISTINCT ORGNR FROM T_TSS_TP WHERE T_TSS_TP.TSS_ID = ?"
//        private const val NAME_QUERY = "SELECT NAVN FROM T_TSS_TP WHERE ORGNR = ?"
//    }
//
//    fun getTpNrsForOrganisation(orgnr: String): List<String> = jdbcTemplate.query(TNFO_QUERY, rowMapper, orgnr)
//    fun getOrganisationName(orgnr: String): List<String> = jdbcTemplate.query(NAME_QUERY, rowMapper, orgnr)
//    fun getOrgNrForOrganisation(tssid: String): List<String> = jdbcTemplate.query(ORGNR_QUERY, rowMapper, tssid)
//}