package no.nav.tpregisteret.database;

import no.nav.tpregisteret.TPOrdning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static no.nav.tpregisteret.TestPerson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private DbConnector db;

    @Test
    public void ingen_forhold_returnerer_tom_liste() {
        String fnr = testPerson1.getFnr();

        List<TPOrdning> expectedForhold = testPerson1.getTpForholdArray();

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
        String fnr = testPerson2.getFnr();

        List<TPOrdning> expectedForhold = testPerson2.getTpForholdArray();

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
    public void flere_forhold_returnerer_flere_tpnummer() {
        String fnr = testPerson3.getFnr();

        List<TPOrdning> expectedForhold = testPerson3.getTpForholdArray();

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
        String fnr = "abcdefghijk";

        List<TPOrdning> expectedForhold = Collections.emptyList();

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
    public void utlands_forhold_ignoreres() {
        //TODO
    }

    @Test
    public void ugyldige_forhold_ignoreres() {
        //TODO
    }
}