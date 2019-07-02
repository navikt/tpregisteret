package no.nav.tpregisteret.domain;

public class TpOrdning {
    private String tssId;
    private String tpId;
    private String orgNr;
    private String navn;

    public TpOrdning() {
    }

    public TpOrdning(String tssId, String tpId, String orgNr, String navn) {
        this.tssId = tssId;
        this.tpId = tpId;
        this.orgNr = orgNr;
        this.navn = navn;
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

    public String getOrgNr() {
        return orgNr;
    }

    public void setOrgNr(String orgNr) {
        this.orgNr = orgNr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}