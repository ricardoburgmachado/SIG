/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoArquivos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
@Entity
@Table(name="template_padrao")
public class TemplatePadrao implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TEMPLATE_PADRAO")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "NOME_TEMPLATE_PADRAO")
    private String nome;
    
    @Lob
    @Basic(optional = true)
    @Column(name = "DADOS_TEMPLATE_PADRAO")
    private byte[] dados;

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
     * @return the dados
     */
    public byte[] getDados() {
        return dados;
    }

    /**
     * @param dados the dados to set
     */
    public void setDados(byte[] dados) {
        this.dados = dados;
    }
    
}
