package no.nav.tpregisteret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbConnector {

    @Autowired
    private JdbcTemplate dbConnection;

    public List<String> getTPIDs(String personIdentifier) {
        String sqlQuery = "SELECT DISTINCT TJPEN.T_TSS_TP.TP_ID AS TPID " +
                "FROM TJPEN.T_FORHOLD " +
                "INNER JOIN TJPEN.T_PERSON " +
                "ON TJPEN.T_PERSON.PERSON_ID = TJPEN.T_FORHOLD.PERSON_ID " +
                "INNER JOIN TJPEN.T_TSS_TP " +
                "ON TJPEN.T_FORHOLD.TSS_EKSTERN_ID_FK = TJPEN.T_TSS_TP.TSS_ID " +
                "WHERE TJPEN.T_PERSON.FNR_FK = ? " +
                "AND TJPEN.T_FORHOLD.ER_GYLDIG=1";

        return dbConnection.queryForList(sqlQuery, String.class, personIdentifier);
    }
}
