/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoArquivos.TemplatePadrao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
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
public class RepositorioTemplatePadrao extends Repositorio{
    
    
    private DaoTemplatePadrao dao;

    @Autowired(required = true)
    public RepositorioTemplatePadrao(DaoTemplatePadrao dao) {
        this.dao = dao;       
    }

    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        TemplatePadrao u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Template Padrao inválido]</br>");
        }else{
            u = (TemplatePadrao)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getDados())){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(u.getDados().length == 0){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return dao.salvar(u);
        }else{
            throw excessao;
        }
    }
    
    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        Excessao excessao = null;
        TemplatePadrao u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Template Padrao inválido]</br>");
        }else{
            u = (TemplatePadrao)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getDados())){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(u.getDados().length == 0){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            int retorno = dao.salvar(u);
            if(desfazerCadastro(request, retorno)){
                return -1;
            }else{
                return retorno;
            }    
        }else{
            throw excessao;
        }
    }

    @Override
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        TemplatePadrao u = null;
        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Template Padrao inválido]</br>");
        }else{
            u = (TemplatePadrao)o;
        }
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
   
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return dao.salvar(u);
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
            System.out.println("O roolback (cadastro template padrão) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }
    
    @Override
    public Object buscarPeloId(int id) throws Excessao {
        TemplatePadrao c = (TemplatePadrao) dao.buscarPeloId(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<TemplatePadrao> c = (ArrayList<TemplatePadrao>) dao.buscarTodos();
        if( verificaNulo(c) && !existeRegistro(c)  ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        if( !dao.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Est TemplatePadrao esta vinculado a uma ou mais entidades no BD.</br>");            
        }else{
            return true;
        }
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            TemplatePadrao c = (TemplatePadrao) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de TemplatePadrão inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        Excessao excessao = null;
        TemplatePadrao c = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Template Padrão inválido]</br>");
        }else{
            c = (TemplatePadrao)o;
        }        
        TemplatePadrao cTemp = (TemplatePadrao) dao.buscarPeloNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        Excessao excessao = null;
        TemplatePadrao cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Template Padrão inválida]</br>");
        }else{
            cParam = (TemplatePadrao)o;
        }        
        Classe cParamBD = (Classe) dao.buscarPeloId(cParam.getId());
        ArrayList<TemplatePadrao> classesBD = (ArrayList<TemplatePadrao>) dao.buscarTodos();
        
        if( verificaNulo(classesBD) ){
            return false;
        }else{
            for(TemplatePadrao cB: classesBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
    }
    
    public TemplatePadrao buscarTemplateDownload(){
    

        String query = "SELECT * FROM template_padrao order by ID_TEMPLATE_PADRAO DESC limit 1";
        return (TemplatePadrao) dao.buscarQuery(query, TemplatePadrao.class );     
    }
    
    
    /*
    private DaoTemplatePadrao daoTemplatePadrao;     
    public RepositorioTemplatePadrao(){
        this.daoTemplatePadrao = new DaoTemplatePadrao();
    }
 
    public Integer salvarTemplatePadrao(TemplatePadrao u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
       
        if(verificaNulo(u.getDados())){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(u.getDados().length == 0){            
            excessao = new Excessao(excessao,"[Arquivo não selecionado]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoTemplatePadrao.salvarTemplatePadrao(u);
        }else{
            throw excessao;
        }
    }
    
    public Integer editarTemplatePadrao(TemplatePadrao u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }
   
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoTemplatePadrao.salvarTemplatePadrao(u);
        }else{
            throw excessao;
        }
    }
    
     public ArrayList<TemplatePadrao> buscarTodos(){
        
        ArrayList<TemplatePadrao> c = daoTemplatePadrao.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public TemplatePadrao buscarTemplatePadrao(int id)throws Excessao{
        TemplatePadrao c = daoTemplatePadrao.buscarTemplate(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public boolean excluirTemplatePadrao(int id) {
        
        if( !daoTemplatePadrao.excluirTemplate(id) ){
            throw new Excessao("[Exclusão não permitida]</br>");            
        }else{
            return true;
        }
    }
    */
    
    
}
