package me.helloworlds.iPou.Model;

public class m_peternak_transaction {
    String id,pembeli,jumlah,verif;

    public m_peternak_transaction(){

    }

    public m_peternak_transaction(String id, String pembeli, String jumlah, String verif) {
        this.id = id;
        this.pembeli = pembeli;
        this.jumlah = jumlah;
        this.verif = verif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getVerif() {
        return verif;
    }

    public void setVerif(String verif) {
        this.verif = verif;
    }
}
