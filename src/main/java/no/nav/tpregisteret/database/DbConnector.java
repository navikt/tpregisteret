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
        String sqlQuery = "SELECT DISTINCT TJPEN.T_TSS_TP.* " +
                "FROM TJPEN.T_FORHOLD " +
                "INNER JOIN TJPEN.T_PERSON " +
                "ON TJPEN.T_PERSON.PERSON_ID = TJPEN.T_FORHOLD.PERSON_ID " +
                "INNER JOIN TJPEN.T_TSS_TP " +
                "ON TJPEN.T_FORHOLD.TSS_EKSTERN_ID_FK = TJPEN.T_TSS_TP.TSS_ID " +
                "WHERE TJPEN.T_PERSON.FNR_FK = ? " +
                "AND TJPEN.T_FORHOLD.ER_GYLDIG=1";

        return (List<TPOrdning>) dbConnection.query(sqlQuery, new BeanPropertyRowMapper(TPOrdning.class), personIdentifier);
    }
}
