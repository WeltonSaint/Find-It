package lps.com.br.find_it;

import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Hanna on 29/10/2016.
 */

public class Item {

    private String nomeItem;
    private ImageView foto;
    private String descricao;
    private String local;
    private Date data;
    private String categoria;
    private String status;

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item(String nomeItem, ImageView foto, String descricao, String local, Date data, String categoria, String status){
        this.nomeItem = nomeItem;
        this.foto = foto;
        this.descricao = descricao;
        this.local = local;
        this.data = data;
        this.categoria = categoria;
        this.status = status;
    }

    public Item(String nomeItem, String descricao, String local, Date data, String categoria, String status){
        this.nomeItem = nomeItem;
        this.descricao = descricao;
        this.local = local;
        this.data = data;
        this.categoria = categoria;
        this.status = status;
    }
}
