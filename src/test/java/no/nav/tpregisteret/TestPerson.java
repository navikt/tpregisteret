package no.nav.tpregisteret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPerson {
    private static final TPOrdning tpOrdning1 = new TPOrdning("11111111111", "1111");
    private static final TPOrdning tpOrdning2 = new TPOrdning("22222222222", "2222");

    public static final TestPerson testPerson1 = new TestPerson("00000000001");
    public static final TestPerson testPerson2 = new TestPerson("00000000002", tpOrdning1);
    public static final TestPerson testPerson3 = new TestPerson("00000000003", tpOrdning1, tpOrdning2);
    public static final TestPerson testPerson4 = new TestPerson("00000000004");
    public static final TestPerson testPerson5 = new TestPerson("00000000005", tpOrdning1);
    public static final TestPerson testPerson6 = new TestPerson("00000000006");
    public static final TestPerson testPerson7 = new TestPerson("00000000007", tpOrdning2);

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
