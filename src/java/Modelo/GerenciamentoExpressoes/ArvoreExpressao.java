/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoExpressoes;

/**
 *
 * @author Ricardo
 */
public interface ArvoreExpressao {
    
    public abstract void setarVariavel(String variavel, double valor);
    
    public abstract void setarExpressao(String expressao);
    
    public abstract Double resolver();
    
    public abstract boolean avaliar();
    
}
