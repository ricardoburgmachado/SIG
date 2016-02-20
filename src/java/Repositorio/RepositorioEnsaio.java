/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioEnsaio extends Repositorio {
    
    private DaoEnsaio dao;

    @Autowired(required = true)
    public RepositorioEnsaio(DaoEnsaio dao) {
        this.dao = dao;       
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Ensaio c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Ensaio inválida]</br>");
        }else{
            c = (Ensaio)o;
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
        Ensaio c = (Ensaio) dao.buscarPeloId(id);
        if( verificaNulo(c) ){            
            return null;
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        return null;
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        return false;
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            Ensaio c = (Ensaio) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Ensaio inválido");
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
    private DaoEnsaio daoEnsaio;    
    public RepositorioEnsaio(){
        this.daoEnsaio=new DaoEnsaio();
    }

    public int salvarEnsaio(Ensaio e){
        return daoEnsaio.salvarEnsaio(e);
    }
 
    public Ensaio buscarEnsaio(int id){
        return daoEnsaio.buscarEnsaio(id);
    }
    */

    
}

