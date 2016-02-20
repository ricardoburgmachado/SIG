/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.Excessoes.MalformedExpressionException;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoExpressoes.Elemento;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
import Modelo.GerenciamentoUsuarios.Usuario;
import com.towel.math.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioParametroPlotagem extends Repositorio {
    
    private DaoParametroPlotagem daoParametroPlotagem;
    private DaoElemento daoElemento;
    
    /**
     * Método construtor com injeção de dependência automática
     * @param DaoParametroPlotagem
     * @param DaoElemento 
     */
    @Autowired(required = true)
    public RepositorioParametroPlotagem(DaoParametroPlotagem daoPP, DaoElemento daoEl) {
        this.daoParametroPlotagem = daoPP;      
        this.daoElemento = daoEl;
    }

    public ArrayList<ParametroPlotagem> buscarPeloUsuario(Usuario u) {
        return daoParametroPlotagem.buscarPeloUsuario(u);
    }    
    
    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");        
        if(roolback){
            request.getSession().setAttribute("roolback", false); 
            daoParametroPlotagem.excluir(id);
            System.out.println("O roolback (cadastro Parametro de Plotagem) foi executado");            
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        Excessao excessao = null;
        ParametroPlotagem u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[ParametroPlotagem inválida]</br>");
        }else{
            u = (ParametroPlotagem)o;
        }
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaVazio(u.getExpressao())){            
            excessao = new Excessao(excessao,"[Campo fórmula vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoParametroPlotagem.salvar(u);
        }else{
            throw excessao;
        }
    }
    
    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {
        Excessao excessao = null;
        ParametroPlotagem u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[ParametroPlotagem inválida]</br>");
        }else{
            u = (ParametroPlotagem)o;
        }
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaVazio(u.getExpressao())){            
            excessao = new Excessao(excessao,"[Campo fórmula vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){            
            int retorno = daoParametroPlotagem.salvar(u);
            //if(desfazerCadastro(request, retorno)){
            //    return -1;
            //}else{
                return retorno;
            //}    
        }else{
            throw excessao;
        }
    }

    @Override
    public Integer editar(Object o) throws Excessao {
        Excessao excessao = null;
        ParametroPlotagem u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[ParametroPlotagem inválida]</br>");
        }else{
            u = (ParametroPlotagem)o;
        }
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaVazio(u.getExpressao())){            
            excessao = new Excessao(excessao,"[Campo fórmula vazio]</br>");
        }
        
        if(verificaRegistroDuplicadoEdicao(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoParametroPlotagem.salvar(u);
        }else{
            throw excessao;
        }        
    }

    @Override
    public Object buscarPeloId(int id) throws Excessao {
        ParametroPlotagem c = (ParametroPlotagem) daoParametroPlotagem.buscarPeloId(id);
        if( verificaNulo(c) ){                  
            return null;
        }else{
            return c;
        }
    }

    @Override
    public boolean excluir(int id) throws Excessao {
        if( !daoParametroPlotagem.excluir(id) ){
            throw new Excessao("[Exclusão não permitida]Este parâmetro esta vinculado a outras entidades.</br>");            
        }else{
            return true;
        }
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            ParametroPlotagem c = (ParametroPlotagem) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de ParametroPlotagem inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        Excessao excessao = null;
        ParametroPlotagem u = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[ParametroPlotagem inválida]</br>");
        }else{
            u = (ParametroPlotagem)o;
        }
        ParametroPlotagem cTemp = (ParametroPlotagem) daoParametroPlotagem.buscarPeloNome(u.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        Excessao excessao = null;
        ParametroPlotagem cParam = null;        
        if( !castValido(o) ){                    
            excessao = new Excessao(excessao,"[Parâmetro de Plotagem inválido]</br>");
        }else{
            cParam = (ParametroPlotagem)o;
        }        
        ParametroPlotagem cParamBD = (ParametroPlotagem) daoParametroPlotagem.buscarPeloId(cParam.getId());
        ArrayList<ParametroPlotagem> classesBD = (ArrayList<ParametroPlotagem>) daoParametroPlotagem.buscarTodos();
        
        if( verificaNulo(classesBD) ){
            return false;
        }else{
            for(ParametroPlotagem cB: classesBD){                
                if(cB.getId() != cParamBD.getId()){//caso o id da classe do BD (sendo lida) seja diferente da classe parametro
                    if( cB.getNome().equalsIgnoreCase(cParam.getNome()) ){//caso a descriçao da classe parametro seja = a classe do banco sendo lida                    
                        return true;
                    }
                }                
            }
        }
        return false;
        
    }

    public static String constroiExpressao(String[] itensFormula ){    
        if(itensFormula != null){
            String item = new String();            
            for(String idItem: itensFormula){                                
                item += idItem+"@";
            }      
           return item;
        }else{
            return null;
        }
    }
    
    /**
     * Utilizado para recuperar do BD
     * @return 
     */
    public String destroiExpressao(String exp){
        System.out.println("metodo destroi: "+exp);
        String[] expSplit = exp.split("@");
        String novaExp = new String();
        for(int i = 0; i< expSplit.length;i++){        
            System.out.println("expSplit[i] ->   "+expSplit[i]);
            if(isInteger(    expSplit[i] )    ){
                Elemento eT = (Elemento) daoElemento.buscarPeloId(Integer.parseInt(expSplit[i]));
                if(eT.getValor() != null){            
                    novaExp += eT.getValor();
                }else{
                    novaExp += eT.getNome();
                }
            }else{
                novaExp += expSplit[i];
            }           
        }        
        return novaExp;
    }    

    @Override
    public List buscarTodos() throws Excessao {
        ArrayList<ParametroPlotagem> c = (ArrayList<ParametroPlotagem>) daoParametroPlotagem.buscarTodos();
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }

    /*
    public boolean validarExpressao(String expressao)throws MalformedExpressionException{ 
        System.out.println("EXPRESSÃO para validar: "+expressao);
        Double result = null;
        try{
            Expression expression = new Expression(expressao);            
            result = expression.resolve();
            System.out.println("Expressão válida -> "+expressao);
            return result != null;            
        }catch(Exception e){
            System.out.println("Expressão inválida -> "+expressao);
            return false;
        }    
    }*/
    
    /*
    public Integer salvar(ParametroPlotagem u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaVazio(u.getExpressao())){            
            excessao = new Excessao(excessao,"[Campo fórmula vazio]</br>");
        }
        
        if(verificaRegistroDuplicado(u)){
            excessao = new Excessao(excessao,"[Nome duplicado]. Já existe um registro com este nome registrado.</br>");           
        }
        
        if(excessao == null ){
            return daoParametroPlotagem.salvar(u);
        }else{
            throw excessao;
        }
    }
    
    
    public Integer editar(ParametroPlotagem u){
        
        Excessao excessao = null;
        
        if(verificaVazio(u.getNome())){            
            excessao = new Excessao(excessao,"[Campo nome vazio]</br>");
        }

        if(verificaVazio(u.getUnidade())){            
            excessao = new Excessao(excessao,"[Campo unidade vazio]</br>");
        }
        
        if(verificaVazio(u.getExpressao())){            
            excessao = new Excessao(excessao,"[Campo fórmula vazio]</br>");
        }
        
        // TEM QUE FAZER UMA VERIFICAÇÃO PARA NOME DUPLICADO PARA EDIÇÃO  
        
        if(excessao == null ){
            return daoParametroPlotagem.salvar(u);
        }else{
            throw excessao;
        }
    }

    
    //public boolean validarExpressao(String[] expressao){
    public boolean validarExpressao(String expressao)throws MalformedExpressionException{ 

        System.out.println("EXPRESSÃO para validar: "+expressao);
        Double result = null;
        try{
            Expression expression = new Expression(expressao);            
            result = expression.resolve();
            return result != null;
        }catch(Exception e){
            return false;
        }    
        
        
    }
    
    
    
    public ArrayList<ParametroPlotagem> buscarTodos(){
        
        ArrayList<ParametroPlotagem> c = daoParametroPlotagem.buscarTodos();      
        if( !existeRegistro(c) ){
            throw new Excessao("[Nenhum registro encontrado]</br>");            
        }else{
            return c;
        }
    }
    
    public ParametroPlotagem buscar(int id)throws Excessao{
        ParametroPlotagem c = (ParametroPlotagem) daoParametroPlotagem.buscarPeloId(id);
        if( verificaNulo(c) ){
            throw new Excessao("[Registro não encontrado]</br>");            
        }else{
            return c;
        }
    }
    */
    /**
     * Utilizado para salvar um registro
     * TRUE = DUPLICADA
     * FALSE = NÃO DUPLICADA
     * @return 
     */
    /*
    private boolean verificaRegistroDuplicado(ParametroPlotagem c){    
        ParametroPlotagem cTemp = (ParametroPlotagem) daoParametroPlotagem.buscarPeloNome(c.getNome());
        if( !verificaNulo(cTemp) ){
            return true;
        }else{
            return false;
        }
    }
    */
     /**
     * Utilizado para editar um registro
     * TRUE = DUPLICADA
     * FALSE = NÃO DUPLICADA
     * @return 
     */
    /*
    private boolean verificaRegistroDuplicadoEdicao(Classe cParam){    
        Classe cParamBD = daoClasse.buscarClasse(cParam.getId());
        ArrayList<Classe> classesBD = daoClasse.buscarTodasClasses();
        
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
    */
    
     /**
     * Utilizado para salvar no BD
     * @param itensFormula
     * @return 
     */
    /*public static String constroiExpressao(Integer[] itensFormula ){    
        if(itensFormula != null){
            String item = new String();            
            for(Integer idItem: itensFormula){                                
                item += idItem+"@";
            }      
           return item;
        }else{
            return null;
        }
    }*/
    

    
    


    
}
