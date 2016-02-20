/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoDadosGeotecnicos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ricado
 */
//@SecondaryTable(name = "ensaio_parametro")
@Entity
@Table(name="ensaio")
public class Ensaio implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ENSAIO")
    private int id;
    
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_CLASSE", referencedColumnName="ID_CLASSE")
    private Classe classe;
    
    @ManyToOne
    @JoinColumn(name="FK_ID_ILHA", referencedColumnName="ID_ILHA")
    private Ilha ilha;
   
    @Transient
    private ArrayList<Parametro> parametros;
    
    public Ensaio(int id){
        this.id=id;
        this.parametros=new ArrayList<>();
    }
    

    public Ensaio(){
        this.parametros=new ArrayList<>();
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

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }

    public void setParametro(Parametro p) {
        this.parametros.add(p);
    }

    
    /**
     * @return the classe
     */
    public Classe getClasse() {
        return classe;
    }

    /**
     * @param classe the classe to set
     */
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
        public void setIlha(Ilha i){
        this.ilha=i;
    }
    
    public Ilha getIlha(){
        return this.ilha;
    }

    
    
    
}
