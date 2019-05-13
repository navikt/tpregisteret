package no.nav.tpregisteret.database;

import no.nav.tpregisteret.TPOrdning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbConnector {

    @Autowired
    private JdbcTemplate dbConnection;

    public List<TPOrdning> getTPOrdninger(String personIdentifier) {
        String sqlQuery =
            "SELECT DISTINCT TSS_ID, TP_ID " +
            "FROM T_TSS_TP " +
                "INNER JOIN T_FORHOLD ON T_FORHOLD.TSS_EKSTERN_ID_FK = T_TSS_TP.TSS_ID " +
                "INNER JOIN T_PERSON ON T_PERSON.PERSON_ID = T_FORHOLD.PERSON_ID " +
            "WHERE T_PERSON.FNR_FK = ? " +
                "AND T_FORHOLD.ER_GYLDIG = 1 " +
                "AND T_FORHOLD.HAR_UTLAND_PENSJ = 0";

        return dbConnection.query(sqlQuery, new BeanPropertyRowMapper<>(TPOrdning.class), personIdentifier);
    }
}
