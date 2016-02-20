/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.Excessoes.MalformedExpressionException;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Resultados;
import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoExpressoes.Elemento;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoExpressoes.ImplementaProcessadorExpressoes;
import Modelo.GerenciamentoExpressoes.Operador;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
import Modelo.GerenciamentoExpressoes.ProcessadorExpressoes;


import Modelo.GerenciamentoExpressoes.Simbolo;
import Modelo.GerenciamentoExpressoes.ValorElemento;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.DaoTipoParametro;
import Repositorio.DaoValor;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioElemento;
import Repositorio.RepositorioEnsaio;
import Repositorio.RepositorioFatorAjuste;
import Repositorio.RepositorioIlha;
import Repositorio.RepositorioParametro;
import Repositorio.RepositorioParametroPlotagem;
import Repositorio.RepositorioPerfil;
import Repositorio.RepositorioTipoParametro;
import Repositorio.RepositorioValor;
import com.towel.math.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.stream.JsonParser;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.SerializationUtils;
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
public class ParametroPlotagemControlador extends ControladorGenerico{

    
    private List<String> inconsistencias;
    @Autowired
    private RepositorioParametroPlotagem repositorioParametroPlotagem;
    private ArrayList<FatorAjuste> fatoresAjuste;
    private JSONArray resultadosArrayJSON;
    @Autowired
    private RepositorioFatorAjuste repositorioFatorAjuste;
    @Autowired
    private RepositorioTipoParametro repositorioTipoParam;
    @Autowired
    private RepositorioParametro repositorioParametro;   
    private ModelAndView modelAndView;
    @Autowired
    private ParametroPlotagem parametroPlotagem;
    
    @Autowired
    ProcessadorExpressoes processadorExpressoes;
    
    
    private TipoParametro tipoParametro;
    private ArrayList<Parametro> parametros;
    private ArrayList<FatorAjuste> fatores;
    private ArrayList<TipoParametro> tiposParametro;

    public ParametroPlotagemControlador() {
        this.modelAndView = new ModelAndView();
    }

    @RequestMapping(value = {"/mostrarModalListarParametroPlotagem"})
    public ModelAndView mostrarModalListarParametros(HttpServletRequest request, ArrayList<ParametroPlotagem> parametros) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarParametroPlotagem");        
        if ( possuiPermissao("ListarParametroPlotagem", request) ) {
            modelAndView.addObject("acesso", true);
            try {
                parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarTodos();
                modelAndView.addObject("parametros", parametros);
            } catch (Excessao di) {
                do {
                    inconsistencias.add(di.getMenssagem());
                    di = di.getExcessao();
                } while (di != null);
            }
            if (inconsistencias.size() > 0) {
                modelAndView.addObject("mensagem", inconsistencias);
                modelAndView.addObject("tipoMensagem", "erro");
            } 
        }else if ( possuiPermissao("ListarParamPlotagemProprios", request) ) {            
            modelAndView.addObject("acesso", true);
            Usuario user = (Usuario) request.getSession().getAttribute("usuario");        
            try {
                parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarPeloUsuario(user);
                modelAndView.addObject("parametros", parametros);
            } catch (Excessao di) {
                do {
                    inconsistencias.add(di.getMenssagem());
                    di = di.getExcessao();
                } while (di != null);
            }
            if (inconsistencias.size() > 0) {
                modelAndView.addObject("mensagem", inconsistencias);
                modelAndView.addObject("tipoMensagem", "erro");
            }
        } else {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        }        
        return modelAndView;
    }

    @RequestMapping(value = {"/validarExpressao"}, method = RequestMethod.POST)
    @ResponseBody
    public boolean validarExpressao(HttpServletRequest request, @RequestParam(value = "expressao", required = false) String expressao) throws MalformedExpressionException {

        return processadorExpressoes.validarExpressao(expressao);
    }

    @RequestMapping(value = {"/mostrarModalCadastroParametroPlotagem"}, method = RequestMethod.POST)
    public ModelAndView mostrarModalCadastrarParametroPlotagem(HttpServletRequest request) throws MalformedExpressionException {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("cadastrarParametroPlotagem");
        
        if (!possuiPermissao("CadastrarParametroPlotagem", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("parametroPlotagem", new ParametroPlotagem());
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {

            try {                
                tiposParametro = (ArrayList<TipoParametro>) repositorioTipoParam.buscarTodos();
                modelAndView.addObject("tiposParametro", tiposParametro);
               
                parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
                modelAndView.addObject("parametros", parametros);
                
                fatoresAjuste = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
                modelAndView.addObject("fatores", fatoresAjuste);
            } catch (Excessao di) {
                do {
                    inconsistencias.add(di.getMenssagem());
                    di = di.getExcessao();
                } while (di != null);
            }
            modelAndView.addObject("parametroPlotagem", new ParametroPlotagem());
            modelAndView.addObject("acesso", true);                  
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/cadastrarParametroPlotagem"}, method = RequestMethod.POST)
    public ModelAndView cadastrarparametroPlotagem(HttpServletRequest request,           
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "unidade", required = false) String unidade,
            @RequestParam(value = "tipoParametro", required = false) Integer idTipoParametro,            
            @RequestParam(value = "itensFormula[]", required = false) String[] itensFormula) {

        modelAndView.setViewName("cadastrarParametroPlotagem");
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");        
        
        parametroPlotagem = new ParametroPlotagem();        
        parametroPlotagem.setNome(nome);
        parametroPlotagem.setUnidade(unidade);
        tipoParametro = new TipoParametro(idTipoParametro);
        parametroPlotagem.setTipoParametro(tipoParametro);
        parametroPlotagem.setUsuario(user);
        int retorno = 0;

        if (itensFormula != null) {
            parametroPlotagem.setExpressao(RepositorioParametroPlotagem.constroiExpressao(itensFormula));
        }

        if (!possuiPermissao("CadastrarParametroPlotagem", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("parametroPlotagem", this.parametroPlotagem);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } 
        
        try {
            modelAndView.addObject("acesso", true);
            modelAndView.addObject("parametroPlotagem", new ParametroPlotagem());
            retorno = repositorioParametroPlotagem.salvar(parametroPlotagem);        
            
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }
        if (inconsistencias.size() > 0) {
            modelAndView.setViewName("cadastrarParametroPlotagem");
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");

        } else {
            if(retorno > 0){
                modelAndView.addObject("tipoMensagem", "sucesso");   
                modelAndView.addObject("mensagem", "Registro efetuado com sucesso!");        
            }else{
                modelAndView.addObject("tipoMensagem", "aviso");   
                modelAndView.addObject("mensagem", "O registro não pôde ser realizado! retorno: "+retorno);        
            }
        }
        
        try{            
            parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
            modelAndView.addObject("parametros", parametros);           
        }catch (Excessao di) { }
        
        try{
            fatores = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();        
            modelAndView.addObject("fatores", fatores);         
        }catch (Excessao di) { }
        
        try{
            
            tiposParametro = (ArrayList<TipoParametro>) repositorioTipoParam.buscarTodos();        
            modelAndView.addObject("tiposParametro", tiposParametro);         
        }catch (Excessao di) { }
        
        return modelAndView;
    }

    
    @RequestMapping(value = {"/editarParametroPlotagem"}, method = RequestMethod.POST)
    public ModelAndView editarParametroPlotagem(HttpServletRequest request  ,          
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "unidade", required = false) String unidade,
            @RequestParam(value = "tipoParametro", required = false) Integer idTipoParametro ,           
            @RequestParam(value = "itensFormula[]", required = false) String[] itensFormula ,
            TipoParametro tipoParametro) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarParametroPlotagem");
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");        
        ArrayList<TipoParametro> tiposParam = null;       
         
        ArrayList<ParametroPlotagem> parametrosPlotagem = null;        
        ArrayList<FatorAjuste> fatores = null;

        parametroPlotagem.setNome(nome);
        parametroPlotagem.setUnidade(unidade);

        tipoParametro .setId(idTipoParametro);
        parametroPlotagem.setTipoParametro(tipoParametro);
        parametroPlotagem.setId(id);
        parametroPlotagem.setUsuario(user);
        if (itensFormula != null) {
            parametroPlotagem.setExpressao(RepositorioParametroPlotagem.constroiExpressao(itensFormula));            
        }
        
        if (!possuiPermissao("EditarParametroPlotagem", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("parametroPlotagem", this.parametroPlotagem);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } 

        
        try {
            modelAndView.addObject("acesso", true);            
            repositorioParametroPlotagem.editar(parametroPlotagem);            
            parametrosPlotagem = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarTodos();
            modelAndView.addObject("parametros", parametrosPlotagem);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }
        if (inconsistencias.size() > 0) {
            modelAndView.setViewName("editarParametroPlotagem");
            modelAndView.addObject("mensagem", inconsistencias);
            modelAndView.addObject("tipoMensagem", "erro");
            
            ParametroPlotagem parametro = (ParametroPlotagem) repositorioParametroPlotagem.buscarPeloId(id);
            modelAndView.addObject("expressaoBD", parametro.getExpressao());
            parametro.setExpressao(repositorioParametroPlotagem.destroiExpressao(parametro.getExpressao()));
            modelAndView.addObject("parametroPlotagem", parametro);                        
        } else {
            modelAndView.addObject("mensagem", "Registro atualizado com sucesso!");
            modelAndView.addObject("tipoMensagem", "sucesso");
        }
        
        try{
            fatores = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();        
            modelAndView.addObject("fatores", fatores);         
        }catch (Excessao di) { }
        
        try{           
            tiposParam = (ArrayList<TipoParametro>) repositorioTipoParam.buscarTodos();        
            modelAndView.addObject("tiposParametro", tiposParam);         
        }catch (Excessao di) { }
        
        return modelAndView;
    }
    
    
    
    @RequestMapping(value = {"/mostrarModalEditarParametroPlotagem"}, method = RequestMethod.GET)
    public ModelAndView mostrarModalEditarParametroPlotagem(@RequestParam(value = "id", required = true, defaultValue = "0") int id, HttpServletRequest request) {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("editarParametroPlotagem");

        if (!possuiPermissao("EditarParametroPlotagem", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("parametroPlotagem", this.parametroPlotagem);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
        }

        try {
            parametroPlotagem = (ParametroPlotagem) repositorioParametroPlotagem.buscarPeloId(id);
            modelAndView.addObject("expressaoBD", parametroPlotagem.getExpressao());
            parametroPlotagem.setExpressao(repositorioParametroPlotagem.destroiExpressao(parametroPlotagem.getExpressao()));
            modelAndView.addObject("parametroPlotagem", parametroPlotagem);                        
        } catch (Excessao di) {
            modelAndView.addObject("parametroPlotagem", new ParametroPlotagem());
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
        
        try {
            tiposParametro = (ArrayList<TipoParametro>) repositorioTipoParam.buscarTodos();            
            modelAndView.addObject("tiposParametro", tiposParametro);
            System.out.println("tiposParametro: "+tiposParametro.size());
        } catch (Excessao di) { }
         
        
            
        try{            
            parametros = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
            modelAndView.addObject("parametros", parametros);            
            System.out.println("parametros: "+parametros.size());
        } catch (Excessao di) { }
         
        try{
            fatores = (ArrayList<FatorAjuste>) repositorioFatorAjuste.buscarTodos();
            modelAndView.addObject("fatores", fatores);             
            System.out.println("fatores: "+fatores.size());
        } catch (Excessao di) { }
            
        return modelAndView;
    }

    @RequestMapping(value = {"/plotarGrafico"}, method = RequestMethod.POST)
    public ModelAndView plotarGrafico(HttpServletRequest request,
            @RequestParam(value = "codigos[]", required = false) Integer[] idsIlhas,
            @RequestParam(value = "fatoresValor[]", required = false) double[] fatoresValor,
            @RequestParam(value = "fatoresId[]", required = false) Integer[] fatoresId,
            @RequestParam(value = "fatoresText[]", required = false) String[] fatoresText,            
            @RequestParam(value = "idsEixoX[]", required = false) Integer[] idsEixoX,            
            @RequestParam(value = "idsCaractY[]", required = false) Integer[] idsCaractY,
            @RequestParam(value = "idsCompressY[]", required = false) Integer[] idsCompressY,
            @RequestParam(value = "idsResistY[]", required = false) Integer[] idsResistY,
            @RequestParam(value = "profundidadeYChecked", required = false) boolean profundidadeYChecked) throws MalformedExpressionException, JSONException {

        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("visualizarGrafico");
        resultadosArrayJSON = new JSONArray();
        try {
            fatoresAjuste = parserFatorAjuste(fatoresValor, fatoresId, fatoresText);
            //resultadosArrayJSON.put(processarExpressoes(idsEixoX, idsIlhas, fatoresAjuste, profundidadeYChecked, obtemIdEixoY(idsCaractY, idsCompressY, idsResistY)));            
            
            resultadosArrayJSON.put(processadorExpressoes.processarExpressoes(idsEixoX, idsIlhas, fatoresAjuste, profundidadeYChecked, obtemIdEixoY(idsCaractY, idsCompressY, idsResistY)));
            
            Resultados resultados = new Resultados();
            resultados.setDados(resultadosArrayJSON);
            request.getSession().setAttribute("resultados", resultados);            
            
            modelAndView.addObject("resultados", resultadosArrayJSON);
        } catch (Excessao di) {
            do {
                inconsistencias.add(di.getMenssagem());
                di = di.getExcessao();
            } while (di != null);
        }

        if (!possuiPermissao("GerarGraficos", request)) {
            modelAndView.addObject("acesso", false);
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");
            modelAndView.addObject("tipoMensagem", "aviso");
            return modelAndView;
        } else {
            modelAndView.addObject("acesso", true);
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

    @RequestMapping(value = {"/excluirParametroPlotagem"}, method = RequestMethod.GET)
    public ModelAndView excluirParametro(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request) {
        
        inconsistencias = new LinkedList<>();
        modelAndView.setViewName("listarParametroPlotagem");
        ArrayList<ParametroPlotagem> parametros;
        
        if( !possuiPermissao("ExcluirParametroPlotagem", request)){
            modelAndView.addObject("acesso",  false);           
            modelAndView.addObject("mensagem", "Você não tem permissão para acessar esta funcionalidade.");        
            modelAndView.addObject("tipoMensagem", "aviso");     
            return modelAndView;
        }else{
            modelAndView.addObject("acesso",  true);
        }
        
        try{
            repositorioParametroPlotagem.excluir(id);
        }catch(Excessao di){
            do {
                inconsistencias.add(di.getMenssagem() );
                di = di.getExcessao();
            } while (di != null);
        }
        parametros = (ArrayList<ParametroPlotagem>) repositorioParametroPlotagem.buscarTodos();
        
        modelAndView.addObject("parametros", parametros);
        if(inconsistencias.size() > 0){            
            modelAndView.addObject("mensagem", inconsistencias);        
            modelAndView.addObject("tipoMensagem", "erro");        
        }else{
            modelAndView.addObject("mensagem", "Registro excluído com sucesso!");        
            modelAndView.addObject("tipoMensagem", "sucesso");        
        }    
        return modelAndView;
    }

    
    
    private int obtemIdEixoY(Integer[] list1, Integer[] list2, Integer[] list3) {
        if (list1 != null) {
            for (Integer in : list1) {
                return in;
            }
        } else if (list2 != null) {
            for (Integer in : list2) {
                return in;
            }
        } else if (list3 != null) {
            for (Integer in : list3) {
                return in;
            }
        }
        return -1;
    }


    /**
     * Responsável por criar um list de FatorAjuste a apartir da junção de suas
     * informações vindas da view
     *
     * @param fatoresValor
     * @param fatoresId
     * @param fatoresTex
     * @return ArrayList<FatorAjuste>
     */
    private ArrayList<FatorAjuste> parserFatorAjuste(double[] fatoresValor, Integer[] fatoresId, String[] fatoresTex) {
        if (fatoresValor != null && fatoresId != null) {
            ArrayList<FatorAjuste> fatoresAjuste = new ArrayList<>();
            for (int i = 0; i < fatoresValor.length; i++) {
                fatoresAjuste.add(new FatorAjuste(fatoresId[i], fatoresValor[i], fatoresTex[i]));
            }
            return fatoresAjuste;
        }
        return null;
    }

    @InitBinder
    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(TipoParametro.class, new TipoParametroPropertyEditor(new RepositorioTipoParametro(new DaoTipoParametro())));
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }


}

 
    
    
    