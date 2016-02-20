package Modelo.GerenciamentoExpressoes;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ricado
 */
@Entity
@Table(name="elemento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_ELEMENTO", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
public class Elemento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ELEMENTO")
    private int id;
    
    @Column(name = "NOME_ELEMENTO")
    private String nome;
    
    @Column(insertable=false, updatable=false, name = "TIPO_ELEMENTO")
    private String tipo;
    
    /*@Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_CLASSE", referencedColumnName="ID_CLASSE")
    private Classe classe;*/
    

    @Transient
    private Double valor;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

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
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the classe
     */
    /*public Classe getClasse() {
        return classe;
    }*/

    /**
     * @param classe the classe to set
     */
    /*public void setClasse(Classe classe) {
        this.classe = classe;
    }*/

    

   

     
     
}
