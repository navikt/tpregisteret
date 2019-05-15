package no.nav.tpregisteret.database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static no.nav.tpregisteret.TestPerson.testPerson1;
import static no.nav.tpregisteret.TestPerson.testPerson2;
import static no.nav.tpregisteret.TestPerson.testPerson3;
import static no.nav.tpregisteret.TestPerson.testPerson4;
import static no.nav.tpregisteret.TestPerson.testPerson6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import no.nav.tpregisteret.TpOrdning;

@SpringBootTest
class DatabaseTests {

    @Autowired
    private TpRepository tpRepository;

    @Test
    void ingen_forhold_returnerer_tom_liste() {
        String fnr = testPerson1.getFnr();

        List<TpOrdning> expectedForhold = testPerson1.getTpForhold();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for (TpOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TpOrdning> actualForhold = tpRepository.getTpOrdningerForPerson(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for (TpOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    void ett_forhold_returnerer_ett_tpnummer() {
        String fnr = testPerson2.getFnr();

        List<TpOrdning> expectedForhold = testPerson2.getTpForhold();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for (TpOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TpOrdning> actualForhold = tpRepository.getTpOrdningerForPerson(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for (TpOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    void flere_forhold_returnerer_flere_tpnummer() {
        String fnr = testPerson3.getFnr();

        List<TpOrdning> expectedForhold = testPerson3.getTpForhold();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for (TpOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TpOrdning> actualForhold = tpRepository.getTpOrdningerForPerson(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for (TpOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    void ugyldig_fnr_returnerer_tom_liste() {
        String fnr = "abcdefghijk";

        List<TpOrdning> expectedForhold = Collections.emptyList();

        List expectedtpIds = new ArrayList<>();
        List expectedtssIds = new ArrayList<>();

        for (TpOrdning tpOrdning : expectedForhold) {
            expectedtpIds.add(tpOrdning.getTpId());
            expectedtssIds.add(tpOrdning.getTssId());
        }

        List<TpOrdning> actualForhold = tpRepository.getTpOrdningerForPerson(fnr);

        List<String> actualTpIds = new ArrayList<>();
        List<String> actualTssIds = new ArrayList<>();

        for (TpOrdning tpOrdning2 : actualForhold) {
            actualTpIds.add(tpOrdning2.getTpId());
            actualTssIds.add(tpOrdning2.getTssId());
        }

        assertEquals(actualTpIds, (expectedtpIds));
        assertEquals(actualTssIds, (expectedtssIds));
    }

    @Test
    void utlands_forhold_ignoreres() {
        List<TpOrdning> utland = testPerson6.getTpForhold();
        List<TpOrdning> fromQuery = tpRepository.getTpOrdningerForPerson(testPerson6.getFnr());

        assertThat(utland.size(), is(1));
        assertThat(fromQuery.size(), is(0));
    }

    @Test
    void ugyldige_forhold_ignoreres() {
        List<TpOrdning> invalid = testPerson4.getTpForhold();
        List<TpOrdning> fromQuery = tpRepository.getTpOrdningerForPerson(testPerson4.getFnr());

        assertThat(invalid.size(), is(1));
        assertThat(fromQuery.size(), is(0));
    }
}