package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Ytelse
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class YtelseRepository(private val jdbcTemplate: JdbcTemplate) {
    companion object {
        private const val YTELSE_BY_ID_QUERY = "SELECT * FROM T_YTELSE WHERE YTELSE_ID = ?"
    }

    fun getYtelseByID(ytelseId: String) = jdbcTemplate.query(YTELSE_BY_ID_QUERY, BeanPropertyRowMapper(Ytelse::class.java), ytelseId)
}