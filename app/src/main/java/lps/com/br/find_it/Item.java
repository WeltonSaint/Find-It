package lps.com.br.find_it;

import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe Item
 *
 * @author Hanna
 * @version 1.0 - 29/10/2016.
 *
 */

public class Item {

    private String nomeItem;
    private ImageView foto;
    private String descricao;
    private double latitude;
    private double longitude;
    private double raio;
    private String data;
    private String categoria;
    private String status;
    private int codigoUsuario;

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

    public double getLatitude() { return latitude;}

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getRaio() { return raio; }

    public void setRaio(double raio) { this.raio = raio; }

    public String getData() { return data;  }

    public void setData(String data) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = df.parse(data);
            this.data = df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            this.data = data;
        }

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

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Item(String nomeItem, ImageView foto, String descricao, double latitude, double longitude, double raio, String categoria, String status, int codigoUsuario) {
        this.nomeItem = nomeItem;
        this.foto = foto;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
        this.categoria = categoria;
        this.status = status;
        this.codigoUsuario = codigoUsuario;
    }

    public Item(String nomeItem, String descricao, double latitude, double longitude, double raio, String categoria, String status, int codigoUsuario) {
        this.nomeItem = nomeItem;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
        this.categoria = categoria;
        this.status = status;
        this.codigoUsuario = codigoUsuario;
    }

    public Item(){}
}
