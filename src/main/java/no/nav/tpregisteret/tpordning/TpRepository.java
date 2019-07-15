package no.nav.tpregisteret.tpordning;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TpRepository {

    private final JdbcTemplate jdbcTemplate;

    public TpRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TpOrdning> getTpOrdningerForPerson(String fnr) {
        String sqlQuery =
                "SELECT DISTINCT TSS_ID, TP_ID, ORGNR, NAVN " +
                        "FROM T_TSS_TP " +
                        "INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID " +
                        "INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID " +
                        "WHERE T_PERSON.FNR_FK = ? " +
                        "AND T_FORHOLD.ER_GYLDIG = 1 " +
                        "AND T_FORHOLD.HAR_UTLAND_PENSJ = 0";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(TpOrdning.class), fnr);
    }

    public List<String> getTpNrsForOrganisation(String orgnr) {
        String sqlQuery =
                "SELECT DISTINCT TP_ID " +
                        "FROM T_TSS_TP " +
                        "WHERE T_TSS_TP.ORGNR = ? ";
        return jdbcTemplate.query(sqlQuery, (rs, i) -> rs.getString(1), orgnr);
    }
}
