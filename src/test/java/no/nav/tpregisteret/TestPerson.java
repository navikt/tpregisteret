package no.nav.tpregisteret;

import no.nav.tpregisteret.domain.TpOrdning;

import java.util.List;

public class TestPerson {
    private static final TpOrdning TP_ORDNING_1 = new TpOrdning("11111111111", "1111");
    private static final TpOrdning TP_ORDNING_2 = new TpOrdning("22222222222", "2222");
    private static final TpOrdning TP_ORDNING_3 = new TpOrdning("33333333333", "3333");

    public static final TestPerson testPerson1 = new TestPerson("00000000001");
    public static final TestPerson testPerson2 = new TestPerson("00000000002", TP_ORDNING_1);
    public static final TestPerson testPerson3 = new TestPerson("00000000003", TP_ORDNING_1, TP_ORDNING_2);
    public static final TestPerson testPerson4 = new TestPerson("00000000004", TP_ORDNING_1);
    public static final TestPerson testPerson5 = new TestPerson("00000000005", TP_ORDNING_1);
    public static final TestPerson testPerson6 = new TestPerson("00000000006", TP_ORDNING_3);
    public static final TestPerson testPerson7 = new TestPerson("00000000007", TP_ORDNING_2);

    private String fnr;
    private List<TpOrdning> tpForhold;

    public TestPerson(String fnr, TpOrdning... tpOrdning) {
        this.fnr = fnr;
        this.tpForhold = List.of(tpOrdning);
    }

    public String getFnr() {
        return fnr;
    }

    public List<TpOrdning> getTpForhold() {
        return tpForhold;
    }
}
