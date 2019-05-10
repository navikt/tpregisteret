package no.nav.tpregisteret.database;

        import no.nav.tpregisteret.TPOrdning;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;

        import java.util.ArrayList;
        import java.util.List;

        import static no.nav.tpregisteret.TestPerson.*;
        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private DbConnector db;

    @Test
    public void ingen_forhold_returnerer_tom_liste() {
        String fnr = ingenForhold.getFnr();

        List<TPOrdning> expectedForhold = ingenForhold.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void ett_forhold_returnerer_ett_tpnummer() {
        String fnr = ettForhold.getFnr();

        List<TPOrdning> expectedForhold = ettForhold.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void to_forhold_returnerer_to_tpnummer() {
        String fnr = toForhold.getFnr();

        List<TPOrdning> expectedForhold = toForhold.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void to_like_forhold_returnerer_ett_tpnummer() {
        String fnr = toLikeForhold.getFnr();

        List<TPOrdning> expectedForhold = toLikeForhold.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void ugyldig_forhold_returnerer_tom_liste() {
        String fnr = ettUgyldigForhold.getFnr();

        List<TPOrdning> expectedForhold = ettUgyldigForhold.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void ugyldig_fnr_returnerer_tom_liste() {
        String fnr = ugyldigFnr.getFnr();

        List<TPOrdning> expectedForhold = ugyldigFnr.getTpForholdArray();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for(TPOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TPOrdning> actualForhold = db.getTPOrdninger(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for(TPOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    public void beskyttelse_mot_sql_injection() {
        String fnr = ingenForhold.getFnr();
        db.getTPOrdninger(fnr + "; DROP TABLE TJPEN.T_FORHOLD --");

        try {
            db.getTPOrdninger(fnr);
        } catch (Exception e) {
            fail("Database table is gone!");
        }
    }
}