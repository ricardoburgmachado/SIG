/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import com.towel.math.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioUsuario extends Repositorio{
    
    private DaoUsuario dao;

    @Autowired(required = true)
    public RepositorioUsuario(DaoUsuario dao) {
        this.dao = dao;       
    }
    
    public Usuario autenticarUsuario(String nome, String senha) throws Excessao{
    
        System.out.println("autenticarUsuario: "+nome+ " "+senha);
        
        Excessao excessao = null;
        Usuario usuarioBd = (Usuario) dao.buscarPeloNomeSenha(nome, senha);
        if( !castValido(usuarioBd) ){                    
            excessao = new Excessao(excessao,"[Usuario inválido]</br>");
        }
        
        if(verificaVazio(nome)){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(senha)){            
            excessao = new Excessao(excessao,"[Campo senha vazio]</br>");
        }
        
        if(verificaNulo(usuarioBd)){            
            excessao = new Excessao(excessao,"[Credenciais inválidas]</br>");
        }
        
        if(excessao == null ){
            Usuario.resetarSenhaUsuario(usuarioBd);
            return usuarioBd;
        }else{
            throw excessao;
        }  
    }
    
    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        Excessao excessao = null;
        Usuario u = null;        
        if( !castValido(o) ){                    
            System.out.println("Usário inválido");
            excessao = new Excessao(excessao,"[Usuario inválido]</br>");
        }else{
            u = (Usuario)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome inválido]</br>");
        }
        
        if(u.getPerfil() == null){            
            excessao = new Excessao(excessao,"[Perfil inválido]</br>");
        }
        
       
        if(verificaVazio(u.getSenha())){            
            excessao = new Excessao(excessao,"[Campo senha vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado</br>");           
        }

        if( u ==  null){
            excessao = new Excessao(excessao,"[Registro nulo]. Objeto nulo.</br>");           
        }        
        
        if(excessao == null ){
            int retorno = dao.salvar(u);
            if(request != null){
                if(desfazerCadastro(request, retorno)){
                    return -1;
                }else{
                    return retorno;
                }            
            }    
            return retorno;
        }else{
            throw excessao;
        }        
    }

    
    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");           
        if(roolback){
            request.getSession().setAttribute("roolback", false); 
            dao.excluir(id);
            System.out.println("O roolback (cadastro usuário) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Usuario u = null;        
        
        if(o == null){
            excessao = new Excessao(excessao,"[Usuario nulo]</br>");
        }
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Usuario inválido]</br>");
        }else{
            u = (Usuario)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaVazio(u.getSenha())){            
            excessao = new Excessao(excessao,"[Campo senha vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado</br>");           
        }

        if( u ==  null){
            excessao = new Excessao(excessao,"[Registro nulo]. Objeto nulo.</br>");           
        }        
        
        if(excessao == null ){
            return dao.salvar(u);
        }else{
            throw excessao;
        }        
    }

    @Override
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        Usuario u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Usuario inválido]</br>");
        }else{
            u = (Usuario)o;
        }
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaVazio(u.getSenha())){            
            excessao = new Excessao(excessao,"[Campo senha vazio]</br>");
        }      
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado</br>");           
        }
        
        if(excessao == null ){
            return dao.salvar(u);
        }else{
            throw excessao;
        }      
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
       
        dao = new DaoUsuario();
        Usuario c = (Usuario) dao.buscarPeloId(id);
        if( verificaNulo(c) ){
            //throw new Excessao("[Registro não encontrado]</br>");            
            return null;
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<Usuario> c = (ArrayList<Usuario>) dao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {        
        return dao.excluir(id);
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            Usuario c = (Usuario) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Usuario inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        Excessao excessao = null;
        Usuario c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Usuario inválido]</br>");
        }else{
            c = (Usuario)o;
        }        
        Usuario cTemp = (Usuario) dao.buscarPeloNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        System.out.println("verificaRegistroDuplicadoEdicao() ");
        Excessao excessao = null;
        Usuario cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Usuário inválido]</br>");
        }else{
            cParam = (Usuario)o;
        }        
        Usuario cParamBD = (Usuario) dao.buscarPeloId(cParam.getId());
        ArrayList<Usuario> usuariosBD = (ArrayList<Usuario>) dao.buscarTodos();
        
        System.out.println("usuBD.size(): "+usuariosBD.size());
        System.out.println("USUARIO BD: "+cParamBD.getNome());
        System.out.println("USUARIO para sobscrever: "+cParam.getNome());
        
        if( verificaNulo(usuariosBD) ){
            return false;
        }else{
            for(Usuario cB: usuariosBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida     
                        System.out.println("O nome do usuário está duplicado na edição");
                        return true;
                    }
                }                
            }
        }
        System.out.println("O nome do usuário NÃO está duplicado na edição");
        return false;
    }    
    
}
