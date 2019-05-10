package no.nav.tpregisteret;

public class TPOrdning {
    private String tssId;
    private String tpId;

    public TPOrdning() {
    }

    public TPOrdning(String tssId, String tpId) {
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