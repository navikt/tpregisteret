package no.nav.tpregisteret;

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringRunner;

        import static no.nav.tpregisteret.TestPerson.*;
        import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
        import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTests {

    @Autowired
    private DbConnector db;

    @Test
    public void ingen_forhold_returnerer_tom_liste() throws Exception {
        String fnr = ingenForhold.getFnr();
        Object[] expectedForhold = ingenForhold.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void ett_forhold_returnerer_ett_tpnummer() throws Exception {
        String fnr = ettForhold.getFnr();
        Object[] expectedForhold = ettForhold.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void to_forhold_returnerer_to_tpnummer() throws Exception {
        String fnr = toForhold.getFnr();
        Object[] expectedForhold = toForhold.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void to_like_forhold_returnerer_ett_tpnummer() throws Exception {
        String fnr = toLikeForhold.getFnr();
        Object[] expectedForhold = toLikeForhold.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void ugyldig_forhold_returnerer_tom_liste() throws Exception {
        String fnr = ettUgyldigForhold.getFnr();
        Object[] expectedForhold = ettUgyldigForhold.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void ugyldig_fnr_returnerer_tom_liste() throws Exception {
        String fnr = ugyldigFnr.getFnr();
        Object[] expectedForhold = ugyldigFnr.getTpForholdArray();

        assertThat(db.getTPIDs(fnr), containsInAnyOrder(expectedForhold));
    }

    @Test
    public void beskyttelse_mot_sql_injection() throws Exception {
        String fnr = ingenForhold.getFnr();
        db.getTPIDs(fnr + "; DROP TABLE TJPEN.T_FORHOLD --");

        try {
            db.getTPIDs(fnr);
        } catch (Exception e) {
            fail("Database table is gone!");
        }
    }
}