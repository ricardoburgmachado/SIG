/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoExpressoes.Operador;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
import Modelo.GerenciamentoExpressoes.Simbolo;
import Modelo.GerenciamentoExpressoes.ValorElemento;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioFatorAjuste;
import Repositorio.RepositorioParametro;
import Repositorio.RepositorioParametroPlotagem;
import Repositorio.RepositorioPerfil;
import Repositorio.RepositorioTipoParametro;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ricado
 */
@Controller
public class FatorAjusteControlador extends ControladorGenerico {

    @Autowired
    private RepositorioFatorAjuste repositorioFatorAjuste;
    private List<String> inconsistencias;
    @Autowired
    private RepositorioParametroPlotagem repositorioParametroPlotagem;
    @Autowired
    private DoublePropertyEditor doublePropertyEditor;
    
    private ModelAndView modelAndView;
    
    @Autowired
    private FatorAjuste fatorAjuste;

    
    FatorAjusteControlador(){
        this.modelAndView = new ModelAndView();
    }
    
    @InitBinder
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Double.TYPE, doublePropertyEditor);
    }

    @RequestMapping(value = {"/mostrarModalListarFatorAjuste"})
    public ModelAndView listarFatoresAjuste(HttpServletRequest request) {

        modelAndView.setViewName("listarFatorAjuste");
        ArrayList<FatorAjuste> fatoresAjuste;
        inconsistencias = new LinkedList<>();

        if (!possuiPermissao("ListarFatorAjuste", request)) {
            modelAndView.addObject("acesso", true);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            fatoresAjuste = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
            repositorioFatorAjuste.buscarTodos();
            modelAndView.addObject("fatoresAjuste", fatoresAjuste);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }
        if (inconsistencias.size() > 0) {
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
        } else {
            modelAndView.addObject("mensagem", null);
            modelAndView.addObject("tipoMensagem", null);
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/mostrarModalCadastrarFatorAjuste"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastrarFatorAjuste(HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("cadastrarFatorAjuste");

        if (!possuiPermissao("CadastrarFatorAjuste", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("fatorAjuste", this.fatorAjuste);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("fatorAjuste", this.fatorAjuste);
            modelAndView.addObject("acesso", true);
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/cadastrarFatorAjuste"}, method = RequestMethod.POST)
    public ModelAndView cadastrarFatorAjuste(HttpServletRequest request, @ModelAttribute("fatorAjuste") FatorAjuste fator) {

        inconsistencias = new LinkedList<>();
        ArrayList<FatorAjuste> fatores;
        modelAndView.setViewName("listarFatorAjuste");
        int retorno = 0;
        if (!possuiPermissao("CadastrarFatorAjuste", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("fatorAjuste", fator);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            retorno = repositorioFatorAjuste.salvar(fator, request);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }

        if (inconsistencias.size() > 0) {
            modelAndView.setViewName("cadastrarFatorAjuste");
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
        } else {
            fatores = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
            modelAndView.addObject("fatoresAjuste", fatores);
            if (retorno > 0) {
                modelAndView.addObject("tipoMensagem", "sucesso");
                modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");
            } else {
                modelAndView.addObject("tipoMensagem", "aviso");
                modelAndView.addObject("mensagem", "O registro não pôde ser realizado!");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/mostrarModalEditarEditarFatorAjuste"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarFatorAjuste(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {

        FatorAjuste fator;
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("editarFatorAjuste");

        if (!possuiPermissao("EditarFatorAjuste", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("fatorAjuste", this.fatorAjuste);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            fator = (FatorAjuste) repositorioFatorAjuste.buscarPeloId(id);
            modelAndView.addObject("fatorAjuste", fator);
        } catch (Excessao di) {
            modelAndView.addObject("fatorAjuste", this.fatorAjuste);
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }

        if (inconsistencias.size() > 0) {
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
        } else {
            modelAndView.addObject("mensagem", null);
            modelAndView.addObject("tipoMensagem", null);
        }
        return modelAndView;

    }

    @RequestMapping(value = {"/editarFatorAjuste"}, method = RequestMethod.POST)
    public ModelAndView editarFatorAjuste(@ModelAttribute("fatorAjuste") FatorAjuste fator, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarFatorAjuste");
        ArrayList<FatorAjuste> fatores;

        if (!possuiPermissao("EditarFatorAjuste", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("fatorAjuste", this.fatorAjuste);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            repositorioFatorAjuste.editar(fator);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }

        if (inconsistencias.size() > 0) {
            modelAndView.setViewName("editarFatorAjuste");
            modelAndView.addObject("fatorAjuste", fator);
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
        } else {
            fatores = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
            modelAndView.addObject("fatoresAjuste", fatores);
            modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");
            modelAndView.addObject("tipoMensagem", "sucesso");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/excluirFatorAjuste"}, method = RequestMethod.GET)
    public ModelAndView excluirFatorAjuste(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarFatorAjuste");

        if (!possuiPermissao("ExcluirFatorAjuste", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            repositorioFatorAjuste.excluir(id);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }
        ArrayList<FatorAjuste> fatoresAjuste = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
        modelAndView.addObject("fatoresAjuste", fatoresAjuste);
        if (inconsistencias.size() > 0) {
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
        } else {
            modelAndView.addObject("mensagem", "Registro excluído com sucesso!");
            modelAndView.addObject("tipoMensagem", "sucesso");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/buscarParametrosFatores"}, method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<FatorAjuste> buscarFatores(@RequestParam(value = "eixoXGlobal[]", required = true, defaultValue = "0") Integer[] idsParamPlotagem, HttpServletRequest request) {

        ArrayList<String> formulas = new ArrayList<>();
        ArrayList<FatorAjuste> fatores = new ArrayList<>();

        if(idsParamPlotagem != null) {
            formulas = adicionaFormulas(idsParamPlotagem);
            fatores = adicionaFatores(formulas);                        
        }

        return fatores;
    }

    private ArrayList<FatorAjuste> adicionaFatores(ArrayList<String> formulas) {
        FatorAjuste fator;
        ArrayList<FatorAjuste> fatores = new ArrayList<>();
        for (String s : formulas) {
            String[] formulaSplit = s.split("@");
            for (int i = 0; i < formulaSplit.length; i++) {
                if (isInteger(formulaSplit[i])) {
                    fator = (FatorAjuste) repositorioFatorAjuste.buscarPeloId(Integer.parseInt(formulaSplit[i]));
                    if (fator != null) {
                        if (!estaContido(fator.getId(), fatores)) {
                            fatores.add(fator);
                            //System.out.println("FATOR DO BD: " + fator.getNome() + " " + fator.getValor());
                        }
                    } else {
                        //System.out.println("FATOR DE AJUSTE NÃO ENCONTRADO COM O ID: " + Integer.parseInt(formulaSplit[i]));
                    }
                }
            }
        }
        return fatores;
    }

    private ArrayList<String> adicionaFormulas(Integer[] idsParamPlotagem) {
        ArrayList<String> formulas = new ArrayList<>();
        for (int i = 0; i < idsParamPlotagem.length; i++) {            
            ParametroPlotagem p;
            p = (ParametroPlotagem) repositorioParametroPlotagem.buscarPeloId(idsParamPlotagem[i]);
                formulas.add(p.getExpressao());          
        }
        return formulas;
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        if (a != null && !a.isEmpty()) {
            ArrayList<FatorAjuste> b = (ArrayList<FatorAjuste>) a;//faz o cast da estrutura
            for (FatorAjuste f : b) {
                if (identificador == f.getId()) {
                    System.out.println("ID: " + identificador + " já está no array");
                    return true;
                } else {
                    System.out.println("ID: " + identificador + " NÃO está no array");
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * return true caso o parametro já esteja presente na estrutura de dados
     */
    /*private boolean contem(int id, ArrayList<FatorAjuste> a){
     for(FatorAjuste f:a){
     if(id == f.getId()){
     System.out.println("ID: "+id+" já está no array");
     return true;
     }else{
     System.out.println("ID: "+id+" NÃO está no array");
     }    
     }
     return false;
     }*/
    
     /*@InitBinder  
     protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)throws Exception {                
     binder.registerCustomEditor(Double.TYPE, new DoublePropertyEditor());  
     }*/

    
}
