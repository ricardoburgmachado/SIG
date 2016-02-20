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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@DiscriminatorValue(value = "S")
public class Simbolo extends Elemento implements Serializable {
    
    
    @Basic(optional = false)
    @Column(name = "VALOR_SIMBOLO")
    private String valor;

    /**
     * @return the valor
     */
    /*@Override
    public String getValor() {
        return valor;
    }*/

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
}
