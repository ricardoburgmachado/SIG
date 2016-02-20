/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoDadosGeotecnicos;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ricado
 */
@Entity
@Table(name="valor")
public class Valor implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VALOR")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "DADOS_VALOR")
    private Double dados;
    
    @ManyToOne
    @JoinColumn(name="FK_ID_PARAMETRO", referencedColumnName="ID_ELEMENTO")
    private Parametro parametro;

    @ManyToOne
    @JoinColumn(name="FK_ID_ENSAIO", referencedColumnName="ID_ENSAIO")
    private Ensaio ensaio;

    
    
    public Valor(){}
    
    public Valor(Ensaio ensaio, Parametro parametro){
    
    }
    
    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }
    /*
    public Ensaio getEnsaio() {
        return ensaio;
    }

    
    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }
    */
    
    /**
     * @return the dados
     */
    public Double getDados() {
        return dados;
    }

    /**
     * @param dados the dados to set
     */
    public void setDados(Double dados) {
        this.dados = dados;
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
     * @return the ensaio
     */
    public Ensaio getEnsaio() {
        return ensaio;
    }

    /**
     * @param ensaio the ensaio to set
     */
    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }

    /**
     * @return the parametro
     */
    /*
    public Parametro getParametro() {
        return parametro;
    }


    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }
    */
}
