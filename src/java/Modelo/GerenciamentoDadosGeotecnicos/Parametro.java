package Modelo.GerenciamentoDadosGeotecnicos;

import Modelo.GerenciamentoExpressoes.Elemento;
import Modelo.GerenciamentoExpressoes.ValorElemento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.stereotype.Component;

@Component
@Entity
@DiscriminatorValue(value = "P")
public class Parametro extends Elemento implements Serializable  {
    
    @Basic(optional = false)
    @Column(name = "UNIDADE_PARAMETRO")
    private String unidade;

    @Basic(optional = false)
    @Column(name = "SIGLA_PARAMETRO")
    private String sigla;    
    
    @Basic(optional = true)
    @Column(name = "NOME_FANTASIA_PARAMETRO")
    private String nomeFantasia;    
    
    @Basic(optional = true)
    @Column(name = "PROFUNDIDADE_CHAVE")
    private boolean profundidadeChave;        
    
    @Transient
    private Ensaio ensaio;
    
    @Transient
    private ArrayList<Valor> valores;       

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_CLASSE", referencedColumnName="ID_CLASSE")
    private Classe classe;
    
    public Parametro(){
        this.valores = new ArrayList<>();        
    }
    
    
    public Parametro(String nome){
        super.setNome(nome);
        this.valores = new ArrayList<>();
    }    

    

    
    /*
    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }
    */

    /**
     * @return the unidade
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * @param unidade the unidade to set
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    
    public ArrayList<Valor> getValores() {
        return valores;
    }

   
    public void setValores(ArrayList<Valor> valores) {
        this.valores = valores;
    }
    
    public Ensaio getEnsaio() {
        return ensaio;
    }
    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }

    /**
     * @return the profundidadeChave
     */
    public boolean isProfundidadeChave() {
        return profundidadeChave;
    }

    /**
     * @param profundidadeChave the profundidadeChave to set
     */
    public void setProfundidadeChave(boolean profundidadeChave) {
        this.profundidadeChave = profundidadeChave;
    }

    /**
     * @return the nomeFantasia
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * @param nomeFantasia the nomeFantasia to set
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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
    
    
    
}
