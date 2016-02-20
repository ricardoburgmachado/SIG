/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoExpressoes.ValorElemento;
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
public class RepositorioFatorAjuste extends Repositorio {
    
    private DaoFatorAjuste daoFatorAjuste;
    
    @Autowired(required = true)
    public RepositorioFatorAjuste(DaoFatorAjuste dao) {
        this.daoFatorAjuste = dao;       
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o){    
        Excessao excessao = null;
        FatorAjuste c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Fator de ajuste inválido]</br>");
        }else{
            c = (FatorAjuste)o;
        }        
        FatorAjuste cTemp = (FatorAjuste) daoFatorAjuste.buscarPorNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");   
        System.out.println("roolback: "+roolback);
        if(roolback){
            request.getSession().setAttribute("roolback", false); 
            daoFatorAjuste.excluir(id);
            System.out.println("O roolback (cadastro Fator de Ajuste) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }
    
    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o){    
        Excessao excessao = null;
        FatorAjuste cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Fator de ajuste inválido]</br>");
        }else{
            cParam = (FatorAjuste)o;
        }        
        FatorAjuste cParamBD = (FatorAjuste) daoFatorAjuste.buscarPeloId(cParam.getId());
        ArrayList<FatorAjuste> fatoresBD = (ArrayList<FatorAjuste>) daoFatorAjuste.buscarTodos();
        
        if( verificaNulo(fatoresBD) ){
            return false;
        }else{
            for(FatorAjuste cB: fatoresBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida                    
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
            FatorAjuste c = (FatorAjuste) o;                       
            return true;
        }catch(ClassCastException e){
            return false;
        }
    }    
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        FatorAjuste u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Fatro de ajuste inválido]</br>");
        }else{
            u = (FatorAjuste)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getValor())){              
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return daoFatorAjuste.salvar(u);
        }else{
            throw excessao;
        }
    }

    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        Excessao excessao = null;
        FatorAjuste u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Fator de ajuste inválido]</br>");
        }else{
            u = (FatorAjuste)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getValor())){              
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            int retorno =  daoFatorAjuste.salvar(u);
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
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        FatorAjuste u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Fatro de ajuste inválido]</br>");
        }else{
            u = (FatorAjuste)o;
        }

        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getValor())){              
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return daoFatorAjuste.salvar(u);
        }else{
            throw excessao;
        }        
        
    }

    @Override
    public Object buscarPeloId(int id) {
        FatorAjuste c = (FatorAjuste) daoFatorAjuste.buscarPeloId(id);
        if( verificaNulo(c) ){            
            return null;
        }else{
            return c;
        }
    }

   @Override
    public boolean excluir(int id) throws Excessao {
        if( !daoFatorAjuste.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Este fator de ajuste esta vinculado a outra entidade.</br>");            
        }else{
            return true;
        }
    }
     
    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<FatorAjuste> c = (ArrayList<FatorAjuste>) daoFatorAjuste.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }       
    }    
    /*
    public RepositorioFatorAjuste()throws Excessao{    
        this.daoFatorAjuste = new DaoFatorAjuste();
    }
    
    public ArrayList<FatorAjuste> buscarTodos(){
        
        ArrayList<FatorAjuste> c = daoFatorAjuste.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro (Fator de Ajuste) encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public Integer salvarFatorAjuste(FatorAjuste u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getValor())){              
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificaEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Possui espaço em branco]</br>");
        }

        
        if(excessao == null ){
            return daoFatorAjuste.salvarFatorAjuste(u);
        }else{
            throw excessao;
        }
    }
    
    
    public Integer editarFatorAjuste(FatorAjuste u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getValor())){              
            excessao = new Excessao(excessao,"[Campo valor vazio]</br>");
        }
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificaEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return daoFatorAjuste.salvarFatorAjuste(u);
        }else{
            throw excessao;
        }
    }
    
    public FatorAjuste buscarFatorAjuste(int id){
    
        return daoFatorAjuste.buscarFatorAjuste(id);
    }
    
    public boolean excluirFator(int id) {
        
        if( !daoFatorAjuste.excluirFatorAjuste(id) ){
            throw new Excessao("[Exclusão não permitida].</br>");            
        }else{
            return true;
        }
    }
        
    private boolean verificaRegistroDuplicado(FatorAjuste c){    
        FatorAjuste fTemp = daoFatorAjuste.buscarFatorAjuste(c.getNome());
        if( !verificaNulo(fTemp) ){
            return true;
        }else{
            return false;
        }
    }

    private boolean verificaRegistroDuplicadoEdicao(FatorAjuste cParam){    
        FatorAjuste cParamBD = daoFatorAjuste.buscarFatorAjuste(cParam.getId());
        ArrayList<FatorAjuste> classesBD = daoFatorAjuste.buscarTodos();
        
        if( verificaNulo(classesBD) ){
            return false;
        }else{
            for(FatorAjuste cB: classesBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da FatorAjuste do BD (sendo lida) seja diferente da FatorAjuste parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da FatorAjuste parametro seja = a FatorAjuste do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
    }
    */


}
