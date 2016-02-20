/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Ricardo
 */
public abstract class Repositorio {
    
    public abstract Integer salvar(Object o) throws Excessao;       
    public abstract Integer salvar(Object o, HttpServletRequest request) throws Excessao;           
    public abstract Integer editar(Object o) throws Excessao;   
    public abstract Object buscarPeloId(int id) throws Excessao ;   
    public abstract List buscarTodos() throws Excessao ;
    public abstract boolean excluir(int id) throws Excessao ;    
    public abstract boolean castValido(Object o) throws Excessao ;
    public abstract boolean verificaRegistroDuplicado(Object o) throws Excessao ;
    public abstract boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao ;
    public abstract boolean desfazerCadastro(HttpServletRequest request, int id);
    
    /**
     * TRUE = NULO
     * FALSE = NÃO NULO
     * @param Object
     * @return boolean
     */
    public boolean verificaNulo(Object obj) {
        return obj == null;
    }
    
    /**
     * TRUE = vazio
     * FALSE = não vazio
     * @param String
     * @return boolean
     */
    public boolean verificaVazio(String campo) {
        return verificaNulo(campo) || campo.equals("") || campo == null;
    }
    
    
     /**
     * TRUE >= 1 registro encontrado
     * FALSE = 0 registro encontrado
     * @param List
     * @return boolean
     */
    public boolean existeRegistro(List registros){    
        
        if( !verificaNulo(registros) && registros.size() > 0)
            return true;
        else
            return false;                    
    }
    
    public boolean verificarEspaco(String s){    
        if(s != null){
            if(s.contains(" "))
                return true;
            else
                return false;                    
        }else{
            return false;
        }
    }

    public boolean isInteger(String s){    
        try{ Integer.parseInt(s); return true;
        }catch(NumberFormatException e){return false;}      
    }

    
    
    
}
