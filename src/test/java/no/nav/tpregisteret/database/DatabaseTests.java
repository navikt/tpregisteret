package no.nav.tpregisteret.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static no.nav.tpregisteret.TestPerson.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private Database database;

    @Test
    public void ingen_forhold_returnerer_tom_liste() throws Exception {
        assertEquals(ingenForhold.getForhold(), database.getTPIDs(ingenForhold.getFnr()));
    }

    @Test
    public void ett_forhold_returnerer_ett_tpnummer() throws Exception {
        assertEquals(ettForhold.getForhold(), database.getTPIDs(ettForhold.getFnr()));
    }

    @Test
    public void to_forhold_returnerer_to_tpnummer() throws Exception {
        assertEquals(toForhold.getForhold(), database.getTPIDs(toForhold.getFnr()));
    }

    @Test
    public void to_like_forhold_returnerer_ett_tpnummer() throws Exception {
        assertEquals(toLikeForhold.getForhold(), database.getTPIDs(toLikeForhold.getFnr()));
    }

    @Test
    public void ugyldig_forhold_returnerer_tom_liste() throws Exception {
        assertEquals(ettUgyldigForhold.getForhold(), database.getTPIDs(ettUgyldigForhold.getFnr()));
    }

    @Test
    public void ugyldig_fnr_returnerer_tom_liste() throws Exception {
        assertEquals(ugyldigFnr.getForhold(), database.getTPIDs(ugyldigFnr.getFnr()));
    }
}