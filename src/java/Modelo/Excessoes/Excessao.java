/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Excessoes;

/**
 *
 * @author ricado
 */
public class Excessao extends RuntimeException  {
    
    private String mensagem;
    private Excessao excessao;

    public Excessao(){}
    
    public Excessao(String mensagem) {
        super(mensagem);
        this.mensagem = mensagem;
    }

    public Excessao(Excessao excessao, String mensagem) {
        super(mensagem, excessao);
        this.mensagem = mensagem;
        this.excessao = excessao;
    }

    public Excessao getExcessao() {
        return excessao;
    }
    
    public String getMenssagem(){
        
        return this.mensagem;
    }

    public void setExcessao(Excessao excessao) {
        this.excessao = excessao;
    }
}
