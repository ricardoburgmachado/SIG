package Modelo.Mapas;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 *
 * @author ricado
 */
public class Marcador {
 
    private int id;
    private double latitude;
    private double longitude;
    private String titulo;

    
    public Marcador(){}
    
    public Marcador(int id, double latitude, double longitude, String titulo){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.titulo = titulo;    
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
}
