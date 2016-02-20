/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Mapas;

import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.RepositorioServicoMapa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ricado
 */
public abstract class ServicoMapa {
    
    private String tipo;
    private double latitudeInicial;
    private double longitudeInicial;
    
    
    
    
    public ServicoMapa(){ }
    
    /*public ServicoMapa(String tipo, double latitudeInicial, double longitudeInicial, String urlServico){
        this.tipo = tipo;
        this.latitudeInicial = latitudeInicial;
        this.longitudeInicial = longitudeInicial;
        this.urlServico = urlServico;
    }*/

    public abstract List<Marcador> buscarRegistrosMarcadores(Usuario u); 
    
    public abstract List<Marcador> buscarRegistrosMarcadores(); 
    
    public abstract ServicoMapa construirMapa(String tipo, double latitudeInicial, double longitudeInicial);
    
    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the latitudeInicial
     */
    public double getLatitudeInicial() {
        return latitudeInicial;
    }

    /**
     * @param latitudeInicial the latitudeInicial to set
     */
    public void setLatitudeInicial(double latitudeInicial) {
        this.latitudeInicial = latitudeInicial;
    }

    /**
     * @return the longitudeInicial
     */
    public double getLongitudeInicial() {
        return longitudeInicial;
    }

    /**
     * @param longitudeInicial the longitudeInicial to set
     */
    public void setLongitudeInicial(double longitudeInicial) {
        this.longitudeInicial = longitudeInicial;
    }

    
    
    
}
