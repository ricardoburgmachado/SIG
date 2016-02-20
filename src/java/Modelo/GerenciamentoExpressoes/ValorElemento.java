
package Modelo.GerenciamentoExpressoes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ricado
 */
//@Table(name="valor_elemento")
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class ValorElemento implements Serializable {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id    
    @Column(name = "ID_VALOR_ELEMENTO")
    private int idValorElemento;
    
    @Basic(optional = true)
    @Column(name = "NOME_VALOR_ELEMENTO")
    private String nome;
    
    @Transient
    private Elemento elemento;
    

    
    public ValorElemento(){}

    /*public ValorElemento(String nome){
        this.nome = nome;
    }*/
    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

   
    
    /*
    public int getIdValElemento() {
        return idValElemento;
    }

    public void setIdValElemento(int idValElemento) {
        this.idValElemento = idValElemento;
    }
    */

    /**
     * @return the id
     */
    public int getId() {
        return idValorElemento;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.idValorElemento = id;
    }

    /**
     * @return the elemento
     */
    public Elemento getElemento() {
        return elemento;
    }

    /**
     * @param elemento the elemento to set
     */
    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }
}
