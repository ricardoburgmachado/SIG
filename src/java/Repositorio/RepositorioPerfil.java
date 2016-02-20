/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
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
public class RepositorioPerfil extends Repositorio{

    private DaoPerfil dao;


    @Autowired(required = true)
    public RepositorioPerfil(DaoPerfil dao) {
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
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
       Perfil c = (Perfil) dao.buscarPeloId(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<Perfil> c = (ArrayList<Perfil>) dao.buscarTodos();
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
    
    /*
    private DaoPerfil daoPerfil;    
    public RepositorioPerfil(){
    
        this.daoPerfil=new DaoPerfil();
    }    
    public Perfil buscarPerfil(int id){
        return daoPerfil.buscarPerfil(id);
    }*/
     
   
    
}
