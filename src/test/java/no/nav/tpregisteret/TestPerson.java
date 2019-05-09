package no.nav.tpregisteret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPerson {

    public static final TestPerson ingenForhold;
    public static final TestPerson ettForhold;
    public static final TestPerson toForhold;
    public static final TestPerson toLikeForhold;
    public static final TestPerson ettUgyldigForhold;
    public static final TestPerson ugyldigFnr;

    static {
        ingenForhold      = new TestPerson("00000000000");
        ettForhold        = new TestPerson("11111111111", "0000");
        toForhold         = new TestPerson("22222222222", "1111", "0000");
        toLikeForhold     = new TestPerson("33333333333", "0000");
        ettUgyldigForhold = new TestPerson("44444444444");
        ugyldigFnr        = new TestPerson("abcdefghijk");
    }

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

    public List<String> getForhold() {
        return tpForhold;
    }
}
