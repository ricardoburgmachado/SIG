
package Modelo.GerenciamentoExpressoes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "O")
public class Operador extends Elemento implements Serializable {

    @Basic(optional = false)
    @Column(name = "VALOR_OPERADOR")
    private String valor;

    public Operador(){}
    
    public Operador(String nome, String valor){    
        super.setNome(nome);
        this.valor = valor;
    }    
    
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
