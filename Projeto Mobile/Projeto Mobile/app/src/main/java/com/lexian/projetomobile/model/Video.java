package com.lexian.projetomobile.model;

public class Video {

    private String titulo="";
    private String descricao="";
    private String data="";
    private String capaVideo="";
    private String videoID="";

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCapaVideo() {
        return capaVideo;
    }

    public void setCapaVideo(String capaVideo) {
        this.capaVideo = capaVideo;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
