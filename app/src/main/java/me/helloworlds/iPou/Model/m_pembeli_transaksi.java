package me.helloworlds.iPou.Model;

public class m_pembeli_transaksi {
    private String id,jumlah,img,status;

    public m_pembeli_transaksi(){

    }

    public m_pembeli_transaksi(String id, String jumlah, String img, String status) {
        this.id = id;
        this.jumlah = jumlah;
        this.img = img;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
