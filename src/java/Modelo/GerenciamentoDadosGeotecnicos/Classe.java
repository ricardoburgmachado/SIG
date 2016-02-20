package Modelo.GerenciamentoDadosGeotecnicos;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
@Entity
@Table(name="classe")
public class Classe implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CLASSE")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "DESCRICAO_CLASSE")
    private String descricao;
     
     
    public Classe(){}
    
    public Classe(int id, String descricao){
        this.id=id;
        this.descricao = descricao;
    }
    
    public Classe(String descricao){
        this.descricao=descricao;
    }
    
    public Classe(int id){
        this.id=id;
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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
