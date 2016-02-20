/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoArquivos.EscritorDocumentoIlha;
import Modelo.GerenciamentoArquivos.ImplementaConstrutorDocumentoIlha;
import Modelo.GerenciamentoArquivos.ImplementaEscritorDocumentoIlha;
import Modelo.GerenciamentoArquivos.ImplemBibliotecaArquivos;
import Modelo.GerenciamentoArquivos.LeitorDocumento;
import Modelo.GerenciamentoArquivos.ImplemLeitorDocumento;
import Modelo.GerenciamentoArquivos.LeitorDocumentoV2;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.ConstrutorEvento;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.ImplementaConstrutorEvento;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.DaoParametro;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioEnsaio;
import Repositorio.RepositorioFatorAjuste;
import Repositorio.RepositorioIlha;
import Repositorio.RepositorioParametro;
import Repositorio.RepositorioParametroPlotagem;
import Repositorio.RepositorioValor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IlhaControlador extends ControladorGenerico {
    
    
    private List<String> inconsistencias;
    @Autowired
    private RepositorioIlha repositorioIlha;
    @Autowired
    private RepositorioParametroPlotagem repositorioParametroPlotagem;   
    private ModelAndView modelAndView;    
    private Ilha ilha;    
    private ArrayList<Ilha> ilhas;
    
    //@Autowired
    private ImplemLeitorDocumento leitorDocumentoV1;
    
    //@Autowired
    private LeitorDocumentoV2 leitorDocumentoV2;

    @Autowired
    private ImplementaConstrutorEvento construtorIlha;
    
    @Autowired
    private RepositorioParametro repositorioParametro;
    @Autowired
    private RepositorioValor repositorioValor;

    public IlhaControlador(){
        this.modelAndView = new ModelAndView();
        this.ilha = new Ilha();        
    }
    
    @RequestMapping(value = {"/mostrarModalListarIlhas"})
    public ModelAndView listarIlhas(HttpServletRequest request) {
 
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarIlhas");
        
        if( possuiPermissao("ListarIlhasProprias", request)){            
            modelAndView.addObject("acesso",  true);
            try{            
                Usuario user = (Usuario) request.getSession().getAttribute("usuario");        
                ilhas = repositorioIlha.buscarPeloUsuario(user);
                modelAndView.addObject("ilhas", ilhas);
            }catch(Excessao di){
                do {
                    inconsistencias.add(di.getMenssagem() );
                    di = di.getExcessao();
                } while (di != null);
            }        
        }else if( possuiPermissao("ListarIlhas", request)){                
            modelAndView.addObject("acesso",  true);
            try{            
                Usuario user = (Usuario) request.getSession().getAttribute("usuario");        
                ilhas = repositorioIlha.buscarTodos();
                modelAndView.addObject("ilhas", ilhas);
            }catch(Excessao di){
                do {
                    inconsistencias.add(di.getMenssagem() );
                    di = di.getExcessao();
                } while (di != null);
            }                    
        }else{
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;            
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
    
    @RequestMapping(value = {"/cadatrarilha"}, method = RequestMethod.POST)
    public ModelAndView cadastrarIlha(@RequestParam(value = "arquivo", required = false) MultipartFile arquivo, 
            HttpServletRequest request) throws IOException {
       
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("cadastrarIlha");       
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        
        int retorno = 0;
        
            try{
                leitorDocumentoV1 = new ImplemLeitorDocumento();
                leitorDocumentoV1.iniciaLeitura(arquivo, new Excessao());                            
                construtorIlha = new ImplementaConstrutorEvento(leitorDocumentoV1, repositorioParametro);
                
                //leitorDocumentoV2 = new LeitorDocumentoV2();
                //leitorDocumentoV2.iniciaLeitura(arquivo, new Excessao());                            
                //construtorIlha = new ImplementaConstrutorEvento(leitorDocumentoV2, repositorioParametro);
                
                ilha = construtorIlha.construirEvento();
                ilha.setUsuario(user);
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&& FINALIZOU A CONSTRUÇÃO");            
                if(ilha != null){
                    retorno = repositorioIlha.salvar(ilha, request);
                }
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&& FINALIZOU O REGISTRO NO BANCO DE DADOS");                        
            }catch(Excessao di){            
                do {
                    inconsistencias.add(di.getMenssagem() );
                    di = di.getExcessao();
                }while(di != null);            
            }
        
        if(inconsistencias.size() > 0){                        
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");             
            modelAndView.addObject("acesso",  true);
        }else{                  
            if( possuiPermissao("ListarIlhasProprias", request)){                        
                try{           
                    ilhas = repositorioIlha.buscarPeloUsuario(user);
                }catch(Excessao di){ }                    
            }else if( possuiPermissao("ListarIlhas", request)){                
                try{
                    ilhas = repositorioIlha.buscarTodos();
                }catch(Excessao di){ }                        
            }            
            modelAndView.setViewName("listarIlhas");
            modelAndView.addObject("acesso",  true);            
            modelAndView.addObject("ilhas", ilhas);       
            if(retorno > 0){
                modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
                modelAndView.addObject("tipoMensagem", "sucesso");        
            }
        }    
                
        return modelAndView;
    }
    
    
   
    @RequestMapping(value = {"/mostrarModalCadastrarIlha"}, method = RequestMethod.POST)
    public ModelAndView mostrarModaCadastrarIlha(HttpServletRequest request) {
       
        modelAndView.setViewName("cadastrarIlha");
        if( !possuiPermissao("CadastrarIlha", request)){
            modelAndView.addObject("acesso",  false);              
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        return modelAndView;
    }
    
    
    @RequestMapping(value = {"/mostrarModalOpcoesParametro"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalOpcoesParametro(HttpServletRequest request){

        modelAndView.setViewName("selecaoOpcoesParametros");
        ArrayList<ParametroPlotagem> parametros;
        inconsistencias = new LinkedList<>();
                    
        if( !possuiPermissao("GerarGraficos", request)){
            modelAndView.addObject("acesso",  false);                   
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
            if ( possuiPermissao("ListarParametroPlotagem", request) ) {
                try{
                     parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarTodos();
                     modelAndView.addObject("parametros", parametros);
                }catch(Excessao di){
                    do {
                        inconsistencias.add(di.getMenssagem() );
                        di = di.getExcessao();
                    } while (di != null);
                }                    
            }else if ( possuiPermissao("ListarParamPlotagemProprios", request) ) {                
                try{
                    //Usuario user = (Usuario) request.getSession().getAttribute("usuario");                            
                    //parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarPeloUsuario(user);                   
                    parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarTodos();
                    modelAndView.addObject("parametros", parametros);
                }catch(Excessao di){
                    do {
                        inconsistencias.add(di.getMenssagem() );
                        di = di.getExcessao();
                    } while (di != null);
                }                                
            }    
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
    
    @RequestMapping(value = {"/excluirIlha"}, method = RequestMethod.GET)
    public ModelAndView excluirIlha(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarIlhas");
        
        if( !possuiPermissao("ExcluirIlha", request)){
            modelAndView.addObject("acesso",  false);           
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
            try{
                repositorioIlha.excluir(id);
                ilhas = repositorioIlha.buscarTodos();
            }catch(Excessao di){
                do {
                    inconsistencias.add(di.getMenssagem() );
                    di = di.getExcessao();
                } while (di != null);
            }
        }

        modelAndView.addObject("ilhas", ilhas);
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", "Registro excluído com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }
    
    @RequestMapping(value = {"/exportar_ilha"}, method = RequestMethod.GET)
    public String exportarIlha(@RequestParam(value = "id", required = true, defaultValue = "0") int idIlha, HttpServletRequest request, HttpServletResponse response) throws IOException, WriteException, RowsExceededException, JSONException{
        
        if( possuiPermissao("ExportarIlha", request)){
            modelAndView.addObject("acesso",  true);
            WritableWorkbook workbook = (WritableWorkbook) gerarEventoResposta(response);
            Ilha ilha =  (Ilha) repositorioIlha.buscarPeloId(idIlha);
            EscritorDocumentoIlha escritorIlha;
            escritorIlha = new ImplementaEscritorDocumentoIlha(workbook, new ImplemBibliotecaArquivos());
            ImplementaConstrutorDocumentoIlha construtorIlha = new ImplementaConstrutorDocumentoIlha(escritorIlha, ilha);
            construtorIlha.construirDocumentoIlha();            
        }
        return "";
    }
    
    @InitBinder
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {}

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
}
