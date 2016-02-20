/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoExpressoes;

import Modelo.Excessoes.MalformedExpressionException;
import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.RepositorioElemento;
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
import javax.persistence.Transient;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
@Entity
@Table(name="parametro_plotagem")
public class ParametroPlotagem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO_PLOTAGEM")
    private int id;
   
    @Basic(optional = false)
    @Column(name = "NOME_PARAMETRO_PLOTAGEM")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "UNIDADE_PARAMETRO_PLOTAGEM")
    private String unidade;
    
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_TIPO_PARAMETRO", referencedColumnName="ID_TIPO_PARAMETRO")
    private TipoParametro tipoParametro;

    @Basic(optional = false)
    @Column(name = "EXPRESSAO_PARAMETRO_PLOTAGEM")
    private String expressao;
    
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_D_USUARIO", referencedColumnName="ID_USUARIO")
    private Usuario usuario;
    
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
     * @return the tipoParametro
     */
    public TipoParametro getTipoParametro() {
        return tipoParametro;
    }

    /**
     * @param tipoParametro the tipoParametro to set
     */
    public void setTipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    /*public Nodo getNodoRaiz() {
        return nodoRaiz;
    }

    
    public void setNodoRaiz(Nodo nodoRaiz) {
        this.nodoRaiz = nodoRaiz;
    }*/

    /**
     * @return the expressao
     */
    public String getExpressao() {
        return expressao;
    }

    /**
     * @param expressao the expressao to set
     */
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
   
    
}
