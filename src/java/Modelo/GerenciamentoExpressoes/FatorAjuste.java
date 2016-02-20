/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoExpressoes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@DiscriminatorValue(value = "F")
public class FatorAjuste extends Elemento implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "VALOR_FATOR")
    private Double valor;
    
    
    public FatorAjuste(){}
    
    public FatorAjuste(int id, Double v, String nome){    
        super.setId(id);
        super.setNome(nome);
        this.valor = v;
    }
    
    /**
     * @return the valor
     */
    @Override
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }


    /*public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }*/
    




    
    
}
