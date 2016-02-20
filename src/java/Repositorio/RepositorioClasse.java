/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 *
 * @author ricado
 */
@Repository
public class RepositorioClasse extends Repositorio  {
    
   
    private DAOClasse dao;

    @Autowired(required = true)
    public RepositorioClasse(DAOClasse dao) {
        this.dao = dao;       
    }
    
    /**
     * Utilizado para salvar um registro
     * TRUE = DUPLICADA
     * FALSE = NÃO DUPLICADA
     * @return 
     */    
    @Override
    public boolean verificaRegistroDuplicado(Object o){    
        Excessao excessao = null;
        Classe c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Classe inválida]</br>");
        }else{
            c = (Classe)o;
        }        
        Classe cTemp = (Classe) dao.buscarPorDescricao(c.getDescricao());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

     /**
     * Utilizado para editar um registro
     * TRUE = DUPLICADA
     * FALSE = NÃO DUPLICADA
     * @param o
     * @return 
     */    
    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o){    
        Excessao excessao = null;
        Classe cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Classe inválida]</br>");
        }else{
            cParam = (Classe)o;
        }        
        Classe cParamBD = (Classe) dao.buscarPeloId(cParam.getId());
        ArrayList<Classe> classesBD = (ArrayList<Classe>) dao.buscarTodos();
        
        if( verificaNulo(classesBD) ){
            return false;
        }else{
            for(Classe cB: classesBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getDescricao().equalsIgnoreCase(cParam.getDescricao()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
    }
    
    
    @Override
    public boolean castValido(Object o){
        try{
            Classe c = (Classe) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Classe inválido");
            return false;
        }
    }    

    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Classe c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Classe inválida]</br>");
        }else{
            c = (Classe)o;
        }
        
        if(verificaVazio(c.getDescricao())){                    
            excessao = new Excessao(excessao,"[Campo descrição vazio]</br>");
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
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        Classe c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Classe inválida]</br>");
        }else{
            c = (Classe)o;
        }
        
        if(verificaVazio(c.getDescricao())){            
            excessao = new Excessao(excessao,"[Campo descrição vazio]</br>");
        }
       
        if(verificaRegistroDuplicadoEdicao(c)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return dao.salvar(c);
        }else{
            throw excessao;
        }
    }

    @Override
    public Object buscarPeloId(int id) {
        Classe c = (Classe) dao.buscarPeloId(id);
        if( verificaNulo(c) ){                  
            return null;
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<Classe> c = (ArrayList<Classe>) dao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        if( !dao.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Esta classe esta vinculada a um ou mais parâmetros.</br>");            
        }else{
            return true;
        }
    }
    
    
    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");           
        if(roolback){
            request.getSession().setAttribute("roolback", false); 
            dao.excluir(id);
            System.out.println("O roolback (cadastro classe) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }

    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        
        Excessao excessao = null;
        Classe c = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Classe inválida]</br>");
        }else{
            c = (Classe)o;
        }
        
        if(verificaVazio(c.getDescricao())){                    
            excessao = new Excessao(excessao,"[Campo descrição vazio]</br>");
        }
       
        if(verificaRegistroDuplicado(c)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){            
            int retorno = dao.salvar(c);
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
    
    
    
    /*
    public Integer salvarClasse(Classe u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getDescricao())){            
            excessao = new Excessao(excessao,"[Campo descrição vazio]</br>");
        }
       
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            //return daoClasse.salvarClasse(u);
            return daoClasse.salvar(u);
        }else{
            throw excessao;
        }
    }

    public Integer editarClasse(Classe u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getDescricao())){            
            excessao = new Excessao(excessao,"[Campo descrição vazio]</br>");
        }
       
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoClasse.salvar(u);
        }else{
            throw excessao;
        }
    }
    
    public ArrayList<Classe> buscarTodasClasses(){
        
        ArrayList<Classe> c = (ArrayList<Classe>) daoClasse.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public Classe buscarClasse(int id)throws Excessao{
        Classe c = (Classe) daoClasse.buscarPeloId(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public boolean excluirClasse(int id) {
        
        if( !daoClasse.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Esta classe esta vinculada a um ou mais parâmetros.</br>");            
        }else{
            return true;
        }
    }
    */

    
}
