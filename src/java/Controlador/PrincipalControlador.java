/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;


import Modelo.Excessoes.MalformedExpressionException;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
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
public class PrincipalControlador extends ControladorGenerico{
    
    private ModelAndView modelAndView;
    
    public PrincipalControlador(){
        this.modelAndView = new ModelAndView();
    }
    
    /**
     * Método responsável por receber todas as requisições para a página principal
     * @param request
     * @param modelAndView
     * @return ModelAndView
     */   
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        
        request.getSession().setAttribute("roolback", false);
        request.getSession().setAttribute("salvarRegistro", false);
        
        System.out.println("Sessão roolback foi criada: "+request.getSession().getAttribute("roolback").toString());
        if(usuario == null){
            System.out.println("USUÁRIO NULO NA PrincipalControlador");
        }else{
           /* inicio debug */
                System.out.println("PrincipalControlador -> USUÁRIO: "+usuario.getNome()+" | PERFIL USUÁRIO: "+usuario.getPerfil().getNome());
                System.out.println("PERMISSõES USUÁRIO: ");
                for(Permissao p: usuario.getPerfil().getPermissoes()){
                    System.out.println("PERMISAO: "+p.getNome());
                }
            /* fim debug */            
        }

        if(usuario == null){
            modelAndView.setViewName("login");
            modelAndView.addObject("usuario", new Usuario());
            return modelAndView;
        }else{
            modelAndView.setViewName("index");
            modelAndView.addObject("usuario", usuario);
            return modelAndView;            
        }        
    }

    
    @RequestMapping(value = {"/sessaoIniciaRoolback"}, method = RequestMethod.GET)
    @ResponseBody
    public boolean sessaoIniciaRoolback(HttpServletRequest request) {
        boolean salvarRegistro = (boolean) request.getSession().getAttribute("salvarRegistro");   
        /*if(salvarRegistro){
            request.getSession().setAttribute("roolback", true);
            System.out.println("O sessão sessaoIniciaRoolback foi criada na controller: "+request.getSession().getAttribute("roolback").toString());
        }else{
            System.out.println("O sessão sessaoIniciaRoolback não foi criada na controller: "+request.getSession().getAttribute("roolback").toString());        
        }*/        
        return true;
    }
    
    
    @RequestMapping(value = {"/sessaoSalvarRegistro"}, method = RequestMethod.GET)
    @ResponseBody
    public boolean sessaoSalvarRegistro(HttpServletRequest request) {
        request.getSession().setAttribute("salvarRegistro", true);
        System.out.println("A sessão salvarRegistro foi criado na controller: "+request.getSession().getAttribute("salvarRegistro").toString());
        return true;
    }
    
    @RequestMapping(value = {"/sessaoCancelarSalvarRegistro"}, method = RequestMethod.GET)
    @ResponseBody
    public boolean sessaoCancelarSalvarRegistro(HttpServletRequest request) {
        request.getSession().setAttribute("salvarRegistro", false);
        System.out.println("A sessão sessaoCancelarSalvarRegistro foi cancelada na controller: "+request.getSession().getAttribute("salvarRegistro").toString());
        return true;
    }

    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
        
}
