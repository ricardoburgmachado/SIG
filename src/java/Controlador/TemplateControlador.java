/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoArquivos.ConstrutorTemplateResultados;
import Modelo.GerenciamentoArquivos.EscritorResultados;
import Modelo.GerenciamentoArquivos.ImplementaConstrutorTemplateResultados;
import Modelo.GerenciamentoArquivos.ImplementaEscritorResultados;
import Modelo.GerenciamentoArquivos.ImplemBibliotecaArquivos;
import Modelo.GerenciamentoArquivos.TemplatePadrao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Resultados;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioTemplatePadrao;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ricado
 */
@Controller
public class TemplateControlador extends ControladorGenerico{
    
    @Autowired
    private RepositorioTemplatePadrao repositorioTemplatePadrao;    
    private List<String> inconsistencias;        
    private ModelAndView modelAndView;
    @Autowired
    private TemplatePadrao templatePadrao;
    
    public TemplateControlador(){
        this.modelAndView = new ModelAndView();
    }
    
    @RequestMapping(value = {"/mostrarModalCadastrarTemplatePadrao"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastrarTemplatePadrao(HttpServletRequest request) {
        
        modelAndView.setViewName("cadastrarTemplatePadrao");
        modelAndView.addObject("templatePadrao",  this.templatePadrao);
        
        if( !possuiPermissao("CadastrarTemplatePadrao", request)){
            modelAndView.addObject("acesso",  false);               
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{                
            modelAndView.addObject("acesso",  true);
        }        
        
        return modelAndView;
    }
            
            
            
    @RequestMapping(value = {"/cadastrarTemplatePadrao"}, method = RequestMethod.POST)
    public ModelAndView cadastrarTemplatePadrao(HttpServletRequest request, @ModelAttribute("templatePadrao") TemplatePadrao template) {

        modelAndView.setViewName("listarTemplatesPadrao");
        ArrayList<TemplatePadrao> templates;
        int retorno = 0;
        
        if( !possuiPermissao("CadastrarTemplatePadrao", request)){
            modelAndView.addObject("acesso",  false);        
            modelAndView.addObject("templatePadrao",  new TemplatePadrao());        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            retorno = repositorioTemplatePadrao.salvar(template, request);
            templates = (ArrayList<TemplatePadrao>) repositorioTemplatePadrao.buscarTodos();
            modelAndView.addObject("templates", templates);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                System.out.println("Excessão: "+di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }
        if(inconsistencias.size() > 0){            
            modelAndView.setViewName("cadastrarTemplatePadrao");
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            if(retorno > 0){
                modelAndView.addObject("tipoMensagem", "sucesso");   
                modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            }else{
                modelAndView.setViewName("cadastrarTemplatePadrao");                
                modelAndView.addObject("tipoMensagem", "aviso");   
                modelAndView.addObject("mensagem", "O registro não pôde ser realizado!");        
            }
        }    
        return modelAndView;        
    }
    
    @RequestMapping(value = {"/editarTemplatePadrao"}, method = RequestMethod.POST)
    public ModelAndView editarTemplatePadrao(@ModelAttribute("templatePadrao") TemplatePadrao template, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("ModelAndView modelAndView = new ModelAndView(\"listarTemplatesPadrao\");");
        ArrayList<TemplatePadrao> templates;
         
        if( !possuiPermissao("EditarTemplatePadrao", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("template",  template);        
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioTemplatePadrao.editar(template);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        
        if(inconsistencias.size() > 0){      
            modelAndView.setViewName("editarTemplatePadrao");
            modelAndView.addObject("template",  template);    
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");            
        }else{
            templates = (ArrayList<TemplatePadrao>) repositorioTemplatePadrao.buscarTodos();
            modelAndView.addObject("templates", templates);
            modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }
    
    
    @RequestMapping(value = {"/mostrarModalEditarTemplatePadrao"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarTemplatePadrao(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        TemplatePadrao template;        
        modelAndView.setViewName("editarTemplatePadrao");
        
        if( !possuiPermissao("EditarTemplatePadrao", request)){
            modelAndView.addObject("acesso",  false);   
            modelAndView.addObject("templatePadrao", new TemplatePadrao());   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{            
           template = (TemplatePadrao) repositorioTemplatePadrao.buscarPeloId(id);
           modelAndView.addObject("templatePadrao", template);
        }catch(Excessao di){
           modelAndView.addObject("templatePadrao", new TemplatePadrao());
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
    
    @RequestMapping(value = {"/mostrarModalListarTemplatePadrao"})
    public ModelAndView listarTemplatePadrao(HttpServletRequest request) {
 
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarTemplatesPadrao");
        ArrayList<TemplatePadrao> templates;
       
        if( !possuiPermissao("ListarTemplatePadrao", request)){
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }        
        
        try{
             templates = (ArrayList<TemplatePadrao>) repositorioTemplatePadrao.buscarTodos();
             modelAndView.addObject("templates", templates);
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
    
    @RequestMapping(value = {"/exportar_dados"}, method = RequestMethod.GET)
    public String exportarDados(HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException, RowsExceededException, JSONException {        

        Resultados resultados = (Resultados) request.getSession().getAttribute("resultados");        
        JSONArray resultadosJSONArray = resultados.getDados();
        iniciaDownload(response, resultadosJSONArray);        
        return "redirect:";
    }
    
    private void iniciaDownload(HttpServletResponse response, JSONArray dados ) throws WriteException, IOException, RowsExceededException, JSONException {
        
        WritableWorkbook workbook;        
        ServletOutputStream outputStream = response.getOutputStream();        
        response.reset();   
        response.setHeader("Content-Disposition", "attachment;filename=dadosExportadosSAPPGAM.xls");  
        response.setContentType("Application/xls");          
        workbook = Workbook.createWorkbook(outputStream);
        EscritorResultados escritor = new ImplementaEscritorResultados(workbook, new ImplemBibliotecaArquivos());
        ConstrutorTemplateResultados constResult = new ImplementaConstrutorTemplateResultados(escritor, dados);
        constResult.construirResultados();
    }

    @InitBinder
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());        
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }

    
    @RequestMapping(value = {"/download_template"}, method = RequestMethod.GET)
    public ModelAndView downloadTemplate(HttpServletRequest request, HttpServletResponse response) {        
        
        TemplatePadrao template = repositorioTemplatePadrao.buscarTemplateDownload();
        response.reset();
        response.setContentType("Application/xls");
        response.setHeader("Content-disposition", "attachment; filename=" + template.getNome() +".xls");
        OutputStream output = null;

        try {
            output = response.getOutputStream();
            output.write(template.getDados());
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
    
    
    /*@InitBinder  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)throws Exception {                        
        binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());        
    } */
     
}
