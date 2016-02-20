/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;


import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.Repositorio;
import Repositorio.RepositorioClasse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
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
public class ClasseControlador extends ControladorGenerico {
    
    @Autowired
    private RepositorioClasse repositorioClasse;
    
    private ModelAndView modelAndView;
    
    @Autowired
    private Classe classe;

    private List<String> inconsistencias;

    ClasseControlador(){   
        this.modelAndView = new ModelAndView();
    }
    
    @RequestMapping(value = {"/cadastrarClasse"}, method = RequestMethod.POST)
    public ModelAndView cadastrarClasse(HttpServletRequest request, @ModelAttribute("classe") Classe classe) throws Excessao {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarClasses");
        ArrayList<Classe> classes;
        int retorno = 0;
        if( !possuiPermissao("CadastrarClasse", request)){
            modelAndView.addObject("acesso",  false);        
            modelAndView.addObject("classe",  this.classe);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            retorno = repositorioClasse.salvar(classe, request);    
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        if(inconsistencias.size() > 0){            
            modelAndView.setViewName("cadastrarClasse");
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            classes = (ArrayList<Classe>) repositorioClasse.buscarTodos();
            modelAndView.addObject("classes", classes);
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
    
    @RequestMapping(value = {"/mostrarModalCadastrarClasse"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastrarClasse(HttpServletRequest request) {
        
        modelAndView.setViewName("cadastrarClasse");
        
        if( !possuiPermissao("CadastrarClasse", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("classe",  this.classe);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("classe",  this.classe);    
            modelAndView.addObject("acesso",  true);
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/mostrarModalListarClasses"})
    public ModelAndView listarClasses(HttpServletRequest request) {
 
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarClasses");
        ArrayList<Classe> classes;               
        
        if( !possuiPermissao("ListarClasses", request)){
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }        
        
        try{
            classes = (ArrayList<Classe>) repositorioClasse.buscarTodos();
            modelAndView.addObject("classes", classes);
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
    
    @RequestMapping(value = {"/mostrarModalEditarClasse"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarClasse(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        Classe classe;       
        modelAndView.setViewName("editarClasse");
        
        if( !possuiPermissao("EditarClasse", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("classe",  this.classe);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{            
           classe = (Classe) repositorioClasse.buscarPeloId(id);
           modelAndView.addObject("classe", classe);
        }catch(Excessao di){
           modelAndView.addObject("classe", this.classe);
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
    
    @RequestMapping(value = {"/editarClasse"}, method = RequestMethod.POST)
    public ModelAndView editarClasse(@ModelAttribute("classe") Classe classe, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarClasses");
        ArrayList<Classe> classes;
         
        if( !possuiPermissao("EditarClasse", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("classe",  new Classe());        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioClasse.editar(classe);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){      
            modelAndView.setViewName("editarClasse");
            modelAndView.addObject("classe",  classe);   
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            classes = (ArrayList<Classe>) repositorioClasse.buscarTodos();
            modelAndView.addObject("classes", classes);
            modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/excluirClasse"}, method = RequestMethod.GET)
    public ModelAndView excluirClasse(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarClasses");
        ArrayList<Classe> classes;
        
        if( !possuiPermissao("ExcluirClasse", request)){
            modelAndView.addObject("acesso",  false);           
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioClasse.excluir(id);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        classes = (ArrayList<Classe>) repositorioClasse.buscarTodos();
        
        modelAndView.addObject("classes", classes);
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", "Registro excluído com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }

    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
    
    
   
    
}
