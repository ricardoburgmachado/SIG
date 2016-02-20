/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ricado
 */
@Repository
public class RepositorioParametro extends Repositorio {

    private DaoParametro dao;

    @Autowired(required = true)
    public RepositorioParametro(DaoParametro dao) {
        this.dao = dao;       
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        Parametro p = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parametro inválida]</br>");
        }else{
            p = (Parametro)o;
        }
        if(verificaNulo(p)){            
            excessao = new Excessao(excessao,"[Parâmetro inválido(nulo)]</br>");
        }        
        
        if(verificaVazio(p.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
        
        if(verificaVazio(p.getSigla())){            
            excessao = new Excessao(excessao,"[Campo sigla vazio]</br>");
        }

        if(verificaVazio(p.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(p)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(p.getNome()) ){              
            excessao = new Excessao(excessao,"[Nome(identificador) possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return dao.salvar(p);
        }else{
            throw excessao;
        }
        
    }
    
    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        Excessao excessao = null;
        Parametro p = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parametro inválida]</br>");
        }else{
            p = (Parametro)o;
        }
        if(verificaNulo(p)){            
            excessao = new Excessao(excessao,"[Parâmetro inválido(nulo)]</br>");
        }        
        
        if(verificaVazio(p.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
        
        if(verificaVazio(p.getSigla())){            
            excessao = new Excessao(excessao,"[Campo sigla vazio]</br>");
        }

        if(verificaVazio(p.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(p)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(p.getNome()) ){              
            excessao = new Excessao(excessao,"[Nome(identificador) possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            int retorno = dao.salvar(p);
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
            System.out.println("O roolback (cadastro Parametro) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }
    
    @Override
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        Parametro u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parametro inválida]</br>");
        }else{
            u = (Parametro)o;
        }
        if(verificaNulo(u)){            
            excessao = new Excessao(excessao,"[Parâmetro inválido(nulo)]</br>");
        }        
        
        if(verificaVazio(u.getNome())){          
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
        
        if(verificaVazio(u.getSigla())){            
            excessao = new Excessao(excessao,"[Campo sigla vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificarEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Nome(identificador) possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return dao.salvar(u);
        }else{
            throw excessao;
        }
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
        Parametro c = (Parametro) dao.buscarPeloId(id);
        if( verificaNulo(c) ){                  
            return null;
        }else{
            return c;
        }
    }
    
    public Object buscarPorNome(String nome) throws Excessao {
        Parametro c = (Parametro) dao.buscarPeloNome(nome);
        if( verificaNulo(c) ){         
            return null;
        }else{
            return c;
        }
    }


    /**
     * Retorna os ids dos parâmetros vinculados ao id do ensaio informado
     * @param idEnsaio
     * @return
     * @throws Excessao 
     */
    public ArrayList<Integer> buscarPorEnsaio(int idEnsaio) throws Excessao {
        ArrayList<Integer> retorno = (ArrayList<Integer>) dao.buscarPorEnsaio(idEnsaio);        
        return retorno;
    }
    
    
    @Override
    public List buscarTodos() throws Excessao {
         ArrayList<Parametro> c = (ArrayList<Parametro>) dao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        if( !dao.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Este Parametro está vinculado a outra entidade.</br>");            
        }else{
            return true;
        }
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            Parametro c = (Parametro) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Parametro inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        Excessao excessao = null;
        Parametro c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parametro inválida]</br>");
        }else{
            c = (Parametro)o;
        }        
        Parametro cTemp = (Parametro) dao.buscarPeloNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        Excessao excessao = null;
        Parametro cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parametro inválida]</br>");
        }else{
            cParam = (Parametro)o;
        }        
        Parametro cParamBD = (Parametro) dao.buscarPeloId(cParam.getId());
        ArrayList<Parametro> classesBD = (ArrayList<Parametro>) dao.buscarTodos();
        
        if( verificaNulo(classesBD) ){
            return false;
        }else{
            for(Parametro cB: classesBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
    }

    
    
   /*
    private DaoParametro daoParametro;    
    public RepositorioParametro(){
        this.daoParametro=new DaoParametro();
    }
    
    public int salvarParametro(Parametro p){
        
        Excessao excessao = null;
        
        if(verificaNulo(p)){            
            excessao = new Excessao(excessao,"[Parâmetro inválido(nulo)]</br>");
        }        
        
        if(verificaVazio(p.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
        
        if(verificaVazio(p.getSigla())){            
            excessao = new Excessao(excessao,"[Campo sigla vazio]</br>");
        }

        if(verificaVazio(p.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(p)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificaEspaco(p.getNome()) ){              
            excessao = new Excessao(excessao,"[Nome(identificador) possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return daoParametro.salvarParametro(p);
        }else{
            throw excessao;
        }
    }

    public Integer editarParametro(Parametro u){
        
        Excessao excessao = null;
        
        if(verificaNulo(u)){            
            excessao = new Excessao(excessao,"[Parâmetro inválido(nulo)]</br>");
        }        
        
        if(verificaVazio(u.getNome())){          
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
        
        if(verificaVazio(u.getSigla())){            
            excessao = new Excessao(excessao,"[Campo sigla vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if( verificaEspaco(u.getNome()) ){              
            excessao = new Excessao(excessao,"[Nome(identificador) possui espaço em branco]</br>");
        }
        
        if(excessao == null ){
            return daoParametro.salvarParametro(u);
        }else{
            throw excessao;
        }
    }
    
    
    public ArrayList<Parametro> buscarParametros() {		
        return daoParametro.buscarParametros();
    }

    public Parametro buscarParametro(int id){
        return daoParametro.buscarParametro(id);
    }
    


    
    
 
    private boolean verificaRegistroDuplicado(Parametro p){    
        Parametro fTemp = daoParametro.buscarParametro(p.getNome());
        if(!verificaNulo(fTemp) ){
            return true;
        }else{
            return false;
        }
    }
    
 
    private boolean verificaRegistroDuplicadoEdicao(Parametro cParam){    
        Parametro cParamBD = daoParametro.buscarParametro(cParam.getId());
        //ArrayList<FatorAjuste> classesBD = daoFatorAjuste.buscarTodos();
        ArrayList<Parametro> parametrosBD = daoParametro.buscarParametros();
        
        if( verificaNulo(parametrosBD) ){
            return false;
        }else{
            for(Parametro cB: parametrosBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da Parametro do BD (sendo lida) seja diferente da Parametro informado por parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da Parametro parametro seja = a Parametro do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
    }
    
    private boolean verificaEspaco(String s){    
        if(s.contains(" "))
            return true;
        else
            return false;                    
    }
    */
    
    
}
