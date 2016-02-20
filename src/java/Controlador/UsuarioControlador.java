/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.DaoPerfil;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioPerfil;
import Repositorio.RepositorioUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;




/**
 *
 * @author ricado
 */
@Controller
public class UsuarioControlador extends ControladorGenerico{
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioPerfil repositorioPerfil;    
    private List<String> inconsistencias;        
    private ModelAndView modelAndView;
    @Autowired
    private PerfilPropertyEditor perfilPropertyEditor;
    @Autowired
    private Usuario usuario;

    public UsuarioControlador(){
        this.modelAndView = new ModelAndView();
    }
    
    @RequestMapping(value = {"/autentica", "autentica"})    
    public ModelAndView autentica(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request, HttpServletResponse response) throws IOException {            
           
            inconsistencias = new LinkedList<>();
            Usuario user;           
            try{
                user = repositorioUsuario.autenticarUsuario(usuario.getNome(), usuario.getSenha());
                request.getSession().setAttribute("usuario", user);
                response.sendRedirect("");
                return null;
            }catch(Excessao di){
                modelAndView.setViewName("login");
                do {
                    inconsistencias.add(di.getMenssagem() );
                    di = di.getExcessao();
                } while (di != null);                
            }            
            if(inconsistencias.size() > 0){            
                modelAndView.addObject("mensagem", inconsistencias);        
                modelAndView.addObject("tipoMensagem", "erro");        
            }else{
                modelAndView.addObject("mensagem", "Login efetuado com sucesso!");        
                modelAndView.addObject("tipoMensagem", "sucesso");        
            }    
            return modelAndView;
    }
    
    
    @RequestMapping(value = {"/sairSistema", "sairSistema"})    
    public ModelAndView sairSistema(HttpServletRequest request, HttpServletResponse response) throws IOException {            

        request.getSession().removeAttribute("usuario");
        response.sendRedirect("");
        return null; 
    }
    
    @RequestMapping(value = {"/cadastrarUsuario"}, method = RequestMethod.POST)
    public ModelAndView cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        ArrayList<Usuario> usuarios;
        modelAndView.setViewName("listarUsuarios");
        ArrayList<Perfil> perfis;
        int retorno = 0;
        
        if( !possuiPermissao("CadastrarUsuario", request)){
            modelAndView.addObject("acesso",  false); 
            modelAndView.addObject("usuario",  this.usuario); 
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            retorno = repositorioUsuario.salvar(usuario, request);            
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){         
            modelAndView.setViewName("cadastrarUsuario");
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
            perfis = (ArrayList<Perfil>) repositorioPerfil.buscarTodos();
            modelAndView.addObject("perfis",  perfis);
        }else{
            usuarios = (ArrayList<Usuario>) repositorioUsuario.buscarTodos();
            modelAndView.addObject("usuarios", usuarios);
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
   
    
    @RequestMapping(value = {"/mostrarModalCadastrarUsuario"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("cadastrarUsuario");
        if( !possuiPermissao("CadastrarUsuario", request)){
            modelAndView.addObject("acesso",  false);          
            modelAndView.addObject("usuario",  this.usuario);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }       
        
        ArrayList<Perfil> perfis;
        
        try{
            modelAndView.addObject("acesso",  true);
            perfis = (ArrayList<Perfil>) repositorioPerfil.buscarTodos();
            modelAndView.addObject("perfis",  perfis);
        }catch(Excessao di){
            modelAndView.addObject("perfis",  null);
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        modelAndView.addObject("usuario", this.usuario);
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", null);        
            modelAndView.addObject("tipoMensagem", null);        
        }    
        return modelAndView;
    }

    @RequestMapping(value = {"/mostrarModalListarUsuarios"}, method = RequestMethod.POST)
    public ModelAndView listarUsuarios(HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarUsuarios");
        ArrayList<Usuario> usuarios;                
        
        if( !possuiPermissao("ListarUsuarios", request)){
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
          
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            usuarios = (ArrayList<Usuario>) repositorioUsuario.buscarTodos();
            modelAndView.addObject("usuarios", usuarios);
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
    
    @RequestMapping(value = {"/mostrarModalEditarUsuario"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarUsuario(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        ArrayList<Perfil> perfis;
        Usuario usuario;        
        modelAndView.setViewName("editarUsuario");
        
        if( !possuiPermissao("EditarUsuario", request)){
            modelAndView.addObject("acesso",  false);          
            modelAndView.addObject("usuario",  this.usuario);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            perfis = (ArrayList<Perfil>) repositorioPerfil.buscarTodos();
            usuario = (Usuario) repositorioUsuario.buscarPeloId(id);
            modelAndView.addObject("perfis",  perfis);
            modelAndView.addObject("usuario", usuario);      
        }catch(Excessao di){
            modelAndView.addObject("perfis",  null);
            modelAndView.addObject("usuario", this.usuario);      
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
    
    @RequestMapping(value = {"/editarUsuario"}, method = RequestMethod.POST)
    public ModelAndView editarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        ArrayList<Usuario> usuarios;
        ArrayList<Perfil> perfis;
        modelAndView.setViewName("listarUsuarios");
        
        if( !possuiPermissao("EditarUsuario", request)){
            modelAndView.addObject("acesso",  false);          
            modelAndView.addObject("usuario",  this.usuario);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioUsuario.editar(usuario);            
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){ 
            modelAndView.setViewName("editarUsuario");
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");   
            perfis = (ArrayList<Perfil>) repositorioPerfil.buscarTodos();
            modelAndView.addObject("perfis",  perfis);
        }else{
            usuarios = (ArrayList<Usuario>) repositorioUsuario.buscarTodos();
            modelAndView.addObject("usuarios", usuarios);
            modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }

    @InitBinder  
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Perfil.class, this.perfilPropertyEditor );  
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
    
    @ResponseBody
    @RequestMapping(value = {"/consultaPermissao"},method = RequestMethod.GET)
    public boolean consultaPermissao(@RequestParam(value = "permissao", required = true) String permissao,HttpServletRequest request) {

        return possuiPermissao(permissao, request);
    }
    
    /*@InitBinder  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)throws Exception {        
        binder.registerCustomEditor(Perfil.class, new PerfilPropertyEditor(new RepositorioPerfil(new DaoPerfil())));  
    }*/ 
    
}
