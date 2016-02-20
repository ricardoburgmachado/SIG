
package Modelo.GerenciamentoUsuarios;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private int id;

    @Basic(optional = false)
    @Column(name = "NOME_USUARIO")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "SENHA_USUARIO")
    private String senha;
    
    @ManyToOne
    @JoinColumn(name="FK_ID_PERFIL", referencedColumnName="ID_PERFIL")
    private Perfil perfil;
    
    public Usuario(){}
    
    
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
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    @Override
    public String toString(){
        return "id: "+id+" | nome: "+nome+" | senha: "+senha+" | idPerfil: "+perfil.getId();
    }
    
    public static void resetarSenhaUsuario(Usuario u){
        u.setSenha(null);
    }

}
