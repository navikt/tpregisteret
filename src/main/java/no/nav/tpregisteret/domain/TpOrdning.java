package no.nav.tpregisteret.domain;

public class TpOrdning {
    private String tssId;
    private String tpId;

    public TpOrdning() {
    }

    public TpOrdning(String tssId, String tpId) {
        this.tssId = tssId;
        this.tpId = tpId;
    }

    public String getTssId() {
        return tssId;
    }

    public void setTssId(String tssId) {
        this.tssId = tssId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }
}