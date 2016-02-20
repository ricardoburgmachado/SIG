/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoExpressoes;

import com.towel.math.Expression;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public class ImplementaArvoreExpressao implements ArvoreExpressao{

    private Expression expression;

    public ImplementaArvoreExpressao(){
        this.expression = new Expression();
    }
    
    @Override
    public void setarVariavel(String variavel, double valor) {
        expression.setVariable(variavel, valor);
    }

    @Override
    public void setarExpressao(String expressao) {
        expression.setExpression(expressao);
    }

    @Override
    public Double resolver() {
        return expression.resolve();
    }

    @Override
    public boolean avaliar() {
        try{
            return expression.resolve() != null;            
        }catch(Exception e){           
            return false;
        }    
    }
    
}
