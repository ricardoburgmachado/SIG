/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.DaoEnsaio;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioEnsaio;
import Repositorio.RepositorioFatorAjuste;
import Repositorio.RepositorioParametro;
import Repositorio.RepositorioPerfil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ricado
 */
@Controller
public class ParametroControlador extends ControladorGenerico{
    
    @Autowired
    private RepositorioParametro repositorioParametro;
   
    private List<String> inconsistencias;    
    
    private ModelAndView modelAndView;
    @Autowired
    private Parametro parametro;
    
    ParametroControlador(){
        this.modelAndView = new ModelAndView();
    }
    
    @RequestMapping(value = {"/mostrarModalListarParametros"})
    public ModelAndView mostrarModalListarParametros(HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarParametros");
        ArrayList<Parametro> parametros;
        
        if( !possuiPermissao("ListarParametros", request)){
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }        
        
        try{
             parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
             modelAndView.addObject("parametros", parametros);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }        
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", null);        
            modelAndView.addObject("tipoMensagem", null);        
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/mostrarModalCadastroParametro"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastroParametro(HttpServletRequest request) {

        modelAndView.setViewName("cadastrarParametro");
        
        if( !possuiPermissao("CadastrarParametro", request)){
            modelAndView.addObject("parametro",  this.parametro);     
            modelAndView.addObject("acesso",  false);                
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{                      
            modelAndView.addObject("parametro",  this.parametro);                 
            modelAndView.addObject("acesso",  true);          
        }    
        return modelAndView;
    }

    @RequestMapping(value = {"/cadastrarParametro"}, method = RequestMethod.POST)
    public ModelAndView cadastrarParametro(HttpServletRequest request, @ModelAttribute("parametro") Parametro parametro) {

        inconsistencias = new LinkedList<>();
        ArrayList<Parametro> parametros;
        modelAndView.setViewName("listarParametros");
        int retorno = 0;
        
        if( !possuiPermissao("CadastrarParametro", request)){
            modelAndView.addObject("acesso",  false);        
            modelAndView.addObject("parametro",  parametro);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            retorno = repositorioParametro.salvar(parametro, request);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){            
            modelAndView.setViewName("cadastrarParametro");
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
            modelAndView.addObject("parametros", parametros);
            if(retorno > 0){
                modelAndView.addObject("tipoMensagem", "sucesso");   
                modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            }else{
                modelAndView.addObject("tipoMensagem", "aviso");   
                modelAndView.addObject("mensagem", "O registro não pôde ser realizado!");        
            }      
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/mostrarModalEditarParametro"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarParametro(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        Parametro parametro;        
        modelAndView.setViewName("editarParametro");
        
        if( !possuiPermissao("EditarParametro", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("parametro",  this.parametro);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{            
           parametro = (Parametro) repositorioParametro.buscarPeloId(id);
           modelAndView.addObject("parametro", parametro);
        }catch(Excessao di){
           modelAndView.addObject("parametro", this.parametro);
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", null);        
            modelAndView.addObject("tipoMensagem", null);        
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/editarParametro"}, method = RequestMethod.POST)
    public ModelAndView editarParametro(@ModelAttribute("parametro") Parametro parametro, HttpServletRequest request) {
        
        ArrayList<Parametro> parametros;
        inconsistencias = new LinkedList<>();
         
        if( !possuiPermissao("EditarParametro", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("parametro",  this.parametro);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioParametro.editar(parametro);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){      
            modelAndView.setViewName("editarParametro");
            modelAndView.addObject("parametro",  parametro);   
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
            modelAndView.addObject("parametros", parametros);
            modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }

    @InitBinder
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Ensaio.class, new EnsaioPropertyEditor(new RepositorioEnsaio(new DaoEnsaio())));  
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
    
    
    /*
     @InitBinder  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)throws Exception {        
        binder.registerCustomEditor(Ensaio.class, new EnsaioPropertyEditor(new RepositorioEnsaio(new DaoEnsaio())));  
    } 
    */
}
