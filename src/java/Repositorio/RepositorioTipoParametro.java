/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoExpressoes.Simbolo;
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
public class RepositorioTipoParametro extends Repositorio{

    private DaoTipoParametro dao;

    @Autowired(required = true)
    public RepositorioTipoParametro(DaoTipoParametro dao) {
        this.dao = dao;       
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer editar(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object buscarPeloId(int id) throws Excessao {
        TipoParametro c = (TipoParametro) dao.buscarPeloId(id);
        if( verificaNulo(c) ){         
            return null;
        }else{
            return c;
        }
    }

    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Object buscarPorNome(String nome) throws Excessao {
        TipoParametro c = (TipoParametro) dao.buscarPorNome(nome);
        if( verificaNulo(c) ){         
            return null;
        }else{
            return c;
        }
    }
    
    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<TipoParametro> c = (ArrayList<TipoParametro>) dao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            TipoParametro c = (TipoParametro) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de TipoParametro inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
    private DaoTipoParametro daoTipoParametro;
    public RepositorioTipoParametro(){
        this.daoTipoParametro = new DaoTipoParametro();
    }
    
    public ArrayList<TipoParametro> buscarTodos(){
        
        ArrayList<TipoParametro> c = daoTipoParametro.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public TipoParametro buscar(int id){
    
        return daoTipoParametro.buscar(id);
    }

    public TipoParametro buscar(String nomeTipoParametro){
    
        return daoTipoParametro.buscar(nomeTipoParametro);
    }
    
    */
    
}
