package me.helloworlds.iPou.Model;

public class m_peternak_schedule {
    String id, kandang, ayammati, ayamsakit;

    public m_peternak_schedule(String id, String kandang, String ayammati, String ayamsakit) {
        this.id = id;
        this.kandang = kandang;
        this.ayammati = ayammati;
        this.ayamsakit = ayamsakit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getAyammati() {
        return ayammati;
    }

    public void setAyammati(String ayammati) {
        this.ayammati = ayammati;
    }

    public String getAyamsakit() {
        return ayamsakit;
    }

    public void setAyamsakit(String ayamsakit) {
        this.ayamsakit = ayamsakit;
    }
}

