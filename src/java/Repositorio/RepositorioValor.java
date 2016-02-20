/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ricardo
 */
@Repository
public class RepositorioValor extends Repositorio{
    
    private DaoValor dao;

    @Autowired(required = true)
    public RepositorioValor(DaoValor dao) {
        this.dao = dao;       
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Valor c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Valor inválido]</br>");
            return -1;
        }else{
            c = (Valor)o;
        }        
        return dao.salvar(c);
    }

    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Valor> buscar(int idParametro, int idEnsaio){
        ArrayList<Valor> c = (ArrayList<Valor>) dao.buscar(idParametro, idEnsaio);
        if( !existeRegistro(c) ){
            //throw new Excessao("[Nenhum registro encontrado]</br>");            
            return null;
        }else{
            return c;
        }
    }
    
    public ArrayList<Valor> buscarProfundidadeChave(int idIlha, int idClasse, int idEnsaio){
        ArrayList<Valor> c = (ArrayList<Valor>) dao.buscarProfundidadeChave(idEnsaio);
        if( !existeRegistro(c) ){
            //throw new Excessao("[Nenhum registro encontrado]</br>");            
            return null;
        }else{
            return c;
        }
    }
    
    @Override
    public Integer editar(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List buscarTodos() throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            Valor c = (Valor) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Valor inválido");
            return false;
        }
    }
    
    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Valor> buscarPorEnsaioParametro(int idEnsaio, int idParametro){
        return dao.buscarPorEnsaioParametro(idEnsaio, idParametro);
    }
    
    
    /*
    private DaoValor daoValor;
    public RepositorioValor(){
        this.daoValor = new DaoValor();
    }
    
    public int salvarValor(Valor v){
        return daoValor.salvarValor(v);
    }
    
    public ArrayList<Valor> buscar(int idIlha, int idParametro, int idEnsaio){
        return daoValor.buscar(idIlha, idParametro, idEnsaio);
    }

    public ArrayList<Valor> buscarProfundidadeChave(int idIlha, int idClasse, int idEnsaio){
        return daoValor.buscarProfundidadeChave(idIlha, idClasse, idEnsaio);
    }
    */


    
}
