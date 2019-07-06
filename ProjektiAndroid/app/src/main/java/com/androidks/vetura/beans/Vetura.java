package com.androidks.vetura.beans;

public class Vetura {
    int id;
    String viti, kilometrazha;
    String titulli,komuna,pershkrimi,tel,lloji;
    byte[] foto;
    String email;


    public Vetura(String titulli, String viti, String kilometrazha,
                  String komuna, String tel, String pershkrimi, String lloji,
                  byte[] img, String email) {
        this.titulli = titulli;
        this.viti = viti;
        this.kilometrazha = kilometrazha;
        this.komuna = komuna;
        this.tel = tel;
        this.pershkrimi = pershkrimi;
        this.lloji = lloji;
        this.foto = img;
        this.email = email;

    }

    public Vetura(String titulli, String pershkrimi, byte[] foto, int id){
        this.id = id;
        this.titulli = titulli;
        this.pershkrimi = pershkrimi;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getViti() {
        return viti;
    }

    public void setViti(String viti) {
        this.viti = viti;
    }

    public String getKilometrazha() {
        return kilometrazha;
    }

    public void setKilometrazha(String kilometrazha) {
        this.kilometrazha = kilometrazha;
    }

    public String getTitulli() {
        return titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }

    public String getKomuna() {
        return komuna;
    }

    public void setKomuna(String komuna) {
        this.komuna = komuna;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }
}

