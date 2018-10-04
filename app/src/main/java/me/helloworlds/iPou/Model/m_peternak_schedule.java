package me.helloworlds.iPou.Model;

public class m_peternak_schedule {
    String id, kandang, ayammati, ayamsakit, count, jum;

    public m_peternak_schedule() {
    }

    public String getCount() {
        return count;
    }

    public String getJum() {
        return jum;
    }

    public void setJum(String jum) {
        this.jum = jum;
    }

    public void setCount(String count) {
        this.count = count;
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

