package no.nav.tpregisteret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPerson {
    static TPOrdning tpOrdning0 = new TPOrdning("11111111111", "1111");
    static TPOrdning tpOrdning1 = new TPOrdning("22222222222", "0000");

    public static final TestPerson ingenForhold      = new TestPerson("00000000000");
    public static final TestPerson ettForhold        = new TestPerson("11111111111", tpOrdning0);
    public static final TestPerson toForhold         = new TestPerson("22222222222", tpOrdning0, tpOrdning1);
    public static final TestPerson toLikeForhold     = new TestPerson("33333333333", tpOrdning0);
    public static final TestPerson ettUgyldigForhold = new TestPerson("44444444444");
    public static final TestPerson ugyldigFnr        = new TestPerson("abcdefghijk");

    private String fnr;
    private List<TPOrdning> tpForhold;

    public TestPerson(String fnr, TPOrdning... tpOrdning) {
        this.fnr = fnr;
        this.tpForhold = new ArrayList<>();

        Collections.addAll(tpForhold, tpOrdning);
    }

    public String getFnr() {
        return fnr;
    }

    public List<TPOrdning> getTpForholdArray() {
        return tpForhold;
    }
}
