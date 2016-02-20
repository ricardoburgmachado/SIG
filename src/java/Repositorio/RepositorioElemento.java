/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoExpressoes.Elemento;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioElemento extends Repositorio{
    
    private DaoElemento dao;
    
    @Autowired(required = true)
    public RepositorioElemento(DaoElemento dao) {
        this.dao = dao;       
    }
    
    @Override
    public boolean castValido(Object o){
        try{
            Elemento c = (Elemento) o;                       
            return true;
        }catch(ClassCastException e){
            return false;
        }
    }    

    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Elemento c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Elemento inválido]</br>");
        }else{
            c = (Elemento)o;
        }
        
        if(verificaVazio(c.getNome())){                    
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(c.getTipo())){                    
            excessao = new Excessao(excessao,"[Campo tipo vazio]</br>");
        }

        
        if(verificaNulo(c.getValor())){                    
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }        

         if(verificaRegistroDuplicado(c)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return dao.salvar(c);
        }else{
            throw excessao;
        }
    }

    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /*
    public RepositorioElemento(){
        this.daoElemento = new DaoElemento();
    }
    
    public Integer salvar(Elemento u){
        return daoElemento.salvar(u);      
    }
    
    public Elemento buscar(int id){      
        return daoElemento.buscar(id);
    }
    
    public ArrayList<Elemento> buscarTodos(){        
        return daoElemento.buscarTodos();
    }
    */

    @Override
    public Integer editar(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
        Elemento c = (Elemento) dao.buscarPeloId(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<Elemento> c = (ArrayList<Elemento>) dao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        if( !dao.excluir(id) ){
            throw new Excessao("[Exclusão não permitida].</br>");            
        }else{
            return true;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        Excessao excessao = null;
        Elemento c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Elemento inválido]</br>");
        }else{
            c = (Elemento)o;
        }        
        Elemento cTemp = (Elemento) dao.buscarPorNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
