/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoUsuarios;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.ws.BindingType;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="perfil")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERFIL")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "NOME_PERFIL")
    private String nome;

    @OneToMany(mappedBy="perfil")
    private List<Usuario> usuarios;
       
    @ManyToMany
    @JoinTable(name="perfil_permissao", joinColumns=
      {@JoinColumn(name="ID_PERFIL")}, inverseJoinColumns=
        {@JoinColumn(name="ID_PERMISSAO")})
    private List<Permissao> permissoes;
    
    public Perfil(){
        this.permissoes=new ArrayList<>();
        this.usuarios=new ArrayList<>();
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
    
    public List<Permissao> getPermissoes() {
        return permissoes;
    }
  
    public void setPermissoes(ArrayList<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
    

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
}
