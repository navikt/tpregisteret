package no.nav.tpregisteret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPerson {

    public static final TestPerson ingenForhold      = new TestPerson("00000000000");
    public static final TestPerson ettForhold        = new TestPerson("11111111111", "0000");
    public static final TestPerson toForhold         = new TestPerson("22222222222", "0000", "1111");
    public static final TestPerson toLikeForhold     = new TestPerson("33333333333", "0000");
    public static final TestPerson ettUgyldigForhold = new TestPerson("44444444444");
    public static final TestPerson ugyldigFnr        = new TestPerson("abcdefghijk");

    private String fnr;
    private List<String> tpForhold;

    public TestPerson(String fnr, String... tpNummer) {
        this.fnr = fnr;
        this.tpForhold = new ArrayList<>();

        Collections.addAll(tpForhold, tpNummer);
    }

    public String getFnr() {
        return fnr;
    }

    public Object[] getTpForholdArray() {
        return tpForhold.toArray();
    }
}
